package edu.uk.le.coursemanagementsystem;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import edu.uk.le.coursemanagementsystem.model.Course;
import edu.uk.le.coursemanagementsystem.model.Enrollment;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CourseAdapter adapter;
    List<Course> courseList;

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int ADD_ITEM_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onResume() {
        super.onResume();
        loadCourses(); // reloads courses when returning to this activity
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EdgeToEdge.enable(this);

        recyclerView = findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button createCourseButton = findViewById(R.id.addcourse_button);
        createCourseButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateCourseActivity.class);
            startActivity(intent);
        });

        loadCourses();
    }

    private void loadCourses() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Course> courseList = AppDB.getDatabase(this).courseDao().getAllCourses();

            runOnUiThread(() -> {
                adapter = new CourseAdapter(courseList, new CourseAdapter.OnCourseClickListener() {
                    @Override
                    public void onCourseClick(Course course) {
                        Intent intent = new Intent(MainActivity.this, CourseDetailsActivity.class);
                        intent.putExtra("course_id", course.getCourseId());
                        startActivity(intent);
                    }

                    @Override
                    public void onCourseLongClick(Course course) {
                        // Show delete confirmation dialog
                        showDeleteCourseDialog(course);
                    }
                });
                recyclerView.setAdapter(adapter);
            });
        });
    }

    private void showDeleteCourseDialog(Course course) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Course")
                .setMessage("Are you sure you want to delete " + course.getCourseName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    deleteCourse(course);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteCourse(Course course) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDB db = AppDB.getDatabase(this);

            // deleting all enrollments for this course
            List<Enrollment> enrollments = db.enrollmentDao().getStudentsInCourse(course.getCourseId());
            for (Enrollment enrollment : enrollments) {
                db.enrollmentDao().deleteEnrollment(enrollment);
            }

            //  deleting the course
            db.courseDao().deleteCourse(course);

            // Refresh list on main thread
            runOnUiThread(() -> {
                Toast.makeText(this, "Course deleted", Toast.LENGTH_SHORT).show();
                loadCourses(); // Reload the list
            });
        });
    }
}