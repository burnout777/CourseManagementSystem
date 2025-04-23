package edu.uk.le.coursemanagementsystem;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import edu.uk.le.coursemanagementsystem.model.Course;


import android.content.Intent;
import android.widget.Button;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EdgeToEdge.enable(this);

        recyclerView = findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CourseAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Observe LiveData from Room database
        AppDB.getDatabase(this)
                .courseDao()
                .getAllCourses()
                .observe(this, new Observer<List<Course>>() {
                    @Override
                    public void onChanged(List<Course> courses) {
                        adapter.setCourses(courses);
                    }
                });

//        Executors.newSingleThreadExecutor().execute(() -> {
//            List<Course> courseList = AppDB.getDatabase(this).courseDao().getAllCourses();
//
//            runOnUiThread(() -> {
//                adapter = new CourseAdapter(courseList);
//                recyclerView.setAdapter(adapter);
//            });
//        });


        Button createCourseButton = findViewById(R.id.addcourse_button);

        createCourseButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateCourseActivity.class);
            startActivity(intent);

        });
    }
}