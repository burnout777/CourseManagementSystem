package edu.uk.le.coursemanagementsystem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.uk.le.coursemanagementsystem.model.Course;


import android.content.Intent;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.List;

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

        Button createCourseButton = findViewById(R.id.addcourse_button);

        createCourseButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateCourseActivity.class);
            startActivity(intent);
        });


    }


}