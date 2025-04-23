package edu.uk.le.coursemanagementsystem;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import edu.uk.le.coursemanagementsystem.model.Course;
import edu.uk.le.coursemanagementsystem.model.Enrollment;
import edu.uk.le.coursemanagementsystem.model.Student;

public class CourseDetailsActivity extends AppCompatActivity {

    private TextView tvCourseCode, tvCourseName, tvLecturerName;
    private RecyclerView rvStudentList;
    private StudentAdapter adapter;
    private List<Student> studentList = new ArrayList<>();
    private long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        // Initialize views
        tvCourseCode = findViewById(R.id.tvCourseCode);
        tvCourseName = findViewById(R.id.tvCourseName);
        tvLecturerName = findViewById(R.id.tvLecturerName);
        rvStudentList = findViewById(R.id.rvStudentList);

        // Get course ID from intent
        courseId = getIntent().getLongExtra("course_id", -1);

        // Set up RecyclerView
        rvStudentList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(studentList);
        rvStudentList.setAdapter(adapter);

        // Load course details and enrolled students
        loadCourseDetails();
    }

    private void loadCourseDetails() {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Get course details from database
            AppDB db = AppDB.getDatabase(this);
            Course course = db.courseDao().getCourseById(courseId);

            // Get enrolled students
            List<Enrollment> enrollments = db.enrollmentDao().getStudentsInCourse(courseId);
            List<Student> students = new ArrayList<>();

            for (Enrollment enrollment : enrollments) {
                Student student = db.studentDao().getStudentById(enrollment.getStudentId());
                if (student != null) {
                    students.add(student);
                }
            }

            // Update UI on main thread
            runOnUiThread(() -> {
                if (course != null) {
                    tvCourseCode.setText(course.getCourseCode());
                    tvCourseName.setText(course.getCourseName());
                    tvLecturerName.setText(course.getLecturerName());
                }

                studentList.clear();
                studentList.addAll(students);
                adapter.notifyDataSetChanged();
            });
        });
    }
}