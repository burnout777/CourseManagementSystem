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

        courseId = getIntent().getIntExtra("COURSE_ID", -1);

        studentDao = AppDB.getDatabase(this).studentDao();
        enrollmentDao = AppDB.getDatabase(this).enrollmentDao();

        addButton.setOnClickListener(v -> {
            String name = nameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String matric = matricField.getText().toString().trim();

            // validation
            if (name.isEmpty() || email.isEmpty() || matric.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Executors.newSingleThreadExecutor().execute(() -> {
                Student existingStudent = studentDao.getStudentByUserName(matric);
                long studentId;

                if (existingStudent != null) {
                    studentId = existingStudent.getStudentId();

                    // checks if already enrolled
                    boolean isEnrolled = enrollmentDao.isStudentEnrolled(courseId, studentId);
                    if (isEnrolled) {
                        runOnUiThread(() -> Toast.makeText(this, "Student already enrolled", Toast.LENGTH_SHORT).show());
                        return;
                    }

                } else {
                    // Insert new students details here
                    Student student = new Student();
                    student.setName(name);
                    student.setEmail(email);
                    student.setUserName(matric);
                    studentId = studentDao.insertStudent(student);
                }

                // Enrolling the student
                Enrollment enrollment = new Enrollment();
                enrollment.setStudentId(studentId);
                enrollment.setCourseId(courseId);
                enrollmentDao.insert(enrollment);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Student added", Toast.LENGTH_SHORT).show();
                    finish();
                });
            });
        });
    }
}