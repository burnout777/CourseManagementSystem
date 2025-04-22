package edu.uk.le.coursemanagementsystem;

import java.util.concurrent.Executors;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import edu.uk.le.coursemanagementsystem.model.Course;

public class CreateCourseActivity extends AppCompatActivity {

    EditText etCode, etName, etLecturer;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        etCode = findViewById(R.id.etCourseCode);
        etName = findViewById(R.id.etCourseName);
        etLecturer = findViewById(R.id.etLecturerName);
        btnCreate = findViewById(R.id.btnCreateCourse);

        btnCreate.setOnClickListener(v -> {
            Course course = new Course();
            course.setCourseCode(etCode.getText().toString().trim());
            course.setCourseName(etName.getText().toString().trim());
            course.setLecturerName(etLecturer.getText().toString().trim());

            Executors.newSingleThreadExecutor().execute(() -> {
                AppDB.getDatabase(CreateCourseActivity.this)
                        .courseDao()
                        .insertCourse(course);
            });

            Toast.makeText(this, "Course created!", Toast.LENGTH_SHORT).show();
            finish(); // Go back to MainActivity
        });
    }
}