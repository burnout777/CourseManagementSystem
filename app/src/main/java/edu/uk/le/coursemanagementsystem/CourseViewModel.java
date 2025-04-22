package edu.uk.le.coursemanagementsystem;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.uk.le.coursemanagementsystem.model.Course;

public class CourseViewModel extends AndroidViewModel {
    private CourseRepo repo;

    private final LiveData<List<Course>> allCourses;

    public CourseViewModel(Application application) {
        super(application);
        repo = new CourseRepo(application);
        allCourses = repo.getAllCourses();
    }

    LiveData<List<Course>> getAllCourses() { return allCourses;
    }

    public void insert(Course course) { repo.insert(course);
    }
}
