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

        String userName = nameField.getText().toString();

        courseId = getIntent().getIntExtra("COURSE_ID", -1);

        addButton.setOnClickListener(v -> {
            Student student = new Student();
            student.setName(nameField.getText().toString().trim());
            student.setEmail(emailField.getText().toString().trim());
            student.setUserName(matricField.getText().toString().trim());




            Executors.newSingleThreadExecutor().execute(() -> {
                long generatedStudentId = AppDB.getDatabase(AddStudentActivity.this)
                        .studentDao()
                        .insertStudent(student);

                Enrollment enrollment = new Enrollment();
                enrollment.setStudentId(generatedStudentId);
                enrollment.setCourseId(courseId);

                AppDB.getDatabase(AddStudentActivity.this)
                        .enrollmentDao().
                        insert(enrollment);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Student added", Toast.LENGTH_SHORT).show();
                    finish();});
            });
        });
    };
}


