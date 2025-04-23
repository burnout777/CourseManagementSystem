package edu.uk.le.coursemanagementsystem;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executors;

import edu.uk.le.coursemanagementsystem.dao.CourseDao;
import edu.uk.le.coursemanagementsystem.dao.EnrollmentDao;
import edu.uk.le.coursemanagementsystem.dao.StudentDao;
import edu.uk.le.coursemanagementsystem.model.Course;
import edu.uk.le.coursemanagementsystem.model.Student;

public class StudentDetailsActivity extends AppCompatActivity {

    private TextView nameText, emailText, matricText;
    private RecyclerView coursesRecyclerView;
    private CourseDao courseDao;
    private int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        nameText = findViewById(R.id.textViewName);
        emailText = findViewById(R.id.textViewEmail);
        matricText = findViewById(R.id.textViewMatric);
        coursesRecyclerView = findViewById(R.id.recyclerViewCourses);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentId = getIntent().getIntExtra("STUDENT_ID", -1);

        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        StudentDao studentDao = db.studentDao();
        EnrollmentDao enrollmentDao = db.enrollmentDao();
        courseDao = db.courseDao();

        Executors.newSingleThreadExecutor().execute(() -> {
            Student student = studentDao.getStudentById(studentId);
            List<Course> enrolledCourses = courseDao.getCoursesForStudent(studentId);

            runOnUiThread(() -> {
                nameText.setText(student.getName());
                emailText.setText(student.getEmail());
                matricText.setText(student.getUserName());

                CourseAdapter adapter = new CourseAdapter(enrolledCourses);
                coursesRecyclerView.setAdapter(adapter);
            });
        });
    }
}

