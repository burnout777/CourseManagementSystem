package edu.uk.le.coursemanagementsystem;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.concurrent.Executors;
import edu.uk.le.coursemanagementsystem.model.Course;

public class ListCoursesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_courses);

        recyclerView = findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Course> courseList = AppDB.getDatabase(this).courseDao().getAllCourses();

            runOnUiThread(() -> {
                adapter = new CourseAdapter(courseList, new CourseAdapter.OnCourseClickListener() {
                    @Override
                    public void onCourseClick(Course course) {
                        // You can leave this empty or implement navigation
                    }

                    @Override
                    public void onCourseLongClick(Course course) {
                        // You can leave this empty
                    }
                });
                recyclerView.setAdapter(adapter);
            });
        });
    }
}