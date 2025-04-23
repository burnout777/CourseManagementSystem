package edu.uk.le.coursemanagementsystem;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executors;

import edu.uk.le.coursemanagementsystem.dao.EnrollmentDao;
import edu.uk.le.coursemanagementsystem.dao.StudentDao;
import edu.uk.le.coursemanagementsystem.model.Enrollment;
import edu.uk.le.coursemanagementsystem.model.Student;

public class AddStudentActivity extends AppCompatActivity {

    private EditText nameField, emailField, matricField;
    private Button addButton;
    private StudentDao studentDao;
    private EnrollmentDao enrollmentDao;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        nameField = findViewById(R.id.editTextName);
        emailField = findViewById(R.id.editTextEmail);
        matricField = findViewById(R.id.editTextMatric);
        addButton = findViewById(R.id.buttonAddStudent);

        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        studentDao = db.studentDao();
        enrollmentDao = db.enrollmentDao();

        courseId = getIntent().getIntExtra("COURSE_ID", -1);

        addButton.setOnClickListener(v -> {
            String name = nameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String userName = matricField.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || userName.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            Executors.newSingleThreadExecutor().execute(() -> {
                Student existing = studentDao.getStudentByUserName(userName);
                int studentId;

                if (existing == null) {
                    Student newStudent = new Student();
                    long id = studentDao.insert(newStudent);
                    studentId = (int) id;
                } else {
                    studentId = Math.toIntExact(existing.getStudentId());
                }

                boolean isEnrolled = enrollmentDao.isStudentEnrolled(courseId, studentId);

                if (isEnrolled) {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Student already enrolled", Toast.LENGTH_SHORT).show()
                    );
                } else {
                    enrollmentDao.insert(new Enrollment());
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Student added", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }
            });
        });
    }
}

