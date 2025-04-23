package edu.uk.le.coursemanagementsystem;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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

        adapter = new CourseAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Observe LiveData from Room
        AppDB.getDatabase(this).courseDao().getAllCourses().observe(this, courseList -> {
            // Update the adapter's data when courseList changes
            adapter.setCourses(courseList);

//        Executors.newSingleThreadExecutor().execute(() -> {
//            List<Course> courseList = AppDB.getDatabase(this).courseDao().getAllCourses();
//
//            runOnUiThread(() -> {
//                adapter = new CourseAdapter(courseList);
//                recyclerView.setAdapter(adapter);
        });
    };
}
