package edu.uk.le.coursemanagementsystem;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executors;

import edu.uk.le.coursemanagementsystem.dao.StudentDao;
import edu.uk.le.coursemanagementsystem.model.Student;

public class EditStudentActivity extends AppCompatActivity {

    private EditText nameField, emailField, matricField;
    private Button saveButton;
    private StudentDao studentDao;
    private long studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        nameField = findViewById(R.id.editTextName);
        emailField = findViewById(R.id.editTextEmail);
        matricField = findViewById(R.id.editTextMatric);
        saveButton = findViewById(R.id.buttonSaveStudent);

        studentId = getIntent().getLongExtra("STUDENT_ID", -1);

        studentDao = AppDB.getDatabase(this).studentDao();

        // Loads  student data
        loadStudentData();

        saveButton.setOnClickListener(v -> {
            String name = nameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String matric = matricField.getText().toString().trim();

            // some simple validation
            if (name.isEmpty() || email.isEmpty() || matric.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Executors.newSingleThreadExecutor().execute(() -> {
                Student student = studentDao.getStudentById(studentId);

                if (student != null) {
                    student.setName(name);
                    student.setEmail(email);
                    student.setUserName(matric);

                    studentDao.updateStudent(student);

                    runOnUiThread(() -> {
                        Toast.makeText(this, "Student updated", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Error: Student not found", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }
            });
        });
    }

    private void loadStudentData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            Student student = studentDao.getStudentById(studentId);

            if (student != null) {
                runOnUiThread(() -> {
                    nameField.setText(student.getName());
                    emailField.setText(student.getEmail());
                    matricField.setText(student.getUserName());
                });
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Error: Student not found", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        });
    }
}