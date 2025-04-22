package edu.uk.le.coursemanagementsystem;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.uk.le.coursemanagementsystem.R;
import edu.uk.le.coursemanagementsystem.AppDB;
import edu.uk.le.coursemanagementsystem.Course;
import edu.uk.le.coursemanagementsystem.CourseAdapter;


import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CourseAdapter adapter;
    List<Course> courseList;

    @Override
    protected void onResume() {
        super.onResume();
        loadCourses();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fabAddCourse);
        fab.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CreateCourseActivity.class)));

        loadCourses();
    }

    private void loadCourses() {
        courseList = AppDB.getInstance(this).courseDao().getAllCourses();
        adapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(adapter);
    }
}