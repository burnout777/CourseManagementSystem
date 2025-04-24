package edu.uk.le.coursemanagementsystem;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import edu.uk.le.coursemanagementsystem.dao.CourseDao;
import edu.uk.le.coursemanagementsystem.dao.StudentDao;
import edu.uk.le.coursemanagementsystem.model.Course;
import edu.uk.le.coursemanagementsystem.model.Student;

public class StudentDetailsActivity extends AppCompatActivity {

    private TextView nameText, emailText, matricText;
    private RecyclerView coursesRecyclerView;
    private long studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        // initialize views
        nameText = findViewById(R.id.textViewName);
        emailText = findViewById(R.id.textViewEmail);
        matricText = findViewById(R.id.textViewMatric);
        coursesRecyclerView = findViewById(R.id.recyclerViewCourses);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get student ID from intent
        studentId = getIntent().getLongExtra("STUDENT_ID", -1);

        if (studentId == -1) {
            Toast.makeText(this, "Error: Student not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // load students details and courses
        loadStudentDetails();
    }

    private void loadStudentDetails() {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDB db = AppDB.getDatabase(getApplicationContext());
            StudentDao studentDao = db.studentDao();
            CourseDao courseDao = db.courseDao();

            // students details
            Student student = studentDao.getStudentById(studentId);

            // Get the courses the student is enrolled in
            List<Course> enrolledCourses = courseDao.getCoursesForStudent((int) studentId);

            runOnUiThread(() -> {
                if (student != null) {
                    nameText.setText(student.getName());
                    emailText.setText(student.getEmail());
                    matricText.setText(student.getUserName());

                    // Set up courses RecyclerView
                    CourseAdapter adapter = new CourseAdapter(enrolledCourses, new CourseAdapter.OnCourseClickListener() {
                        @Override
                        public void onCourseClick(Course course) {
                            // Navigates to courses details if needed
                        }

                        @Override
                        public void onCourseLongClick(Course course) {
                        }
                    });
                    coursesRecyclerView.setAdapter(adapter);

                    if (enrolledCourses.isEmpty()) {
                        Toast.makeText(StudentDetailsActivity.this,
                                "Student not enrolled in any courses",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(StudentDetailsActivity.this,
                            "Error: Student not found in database",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        });
    }
}