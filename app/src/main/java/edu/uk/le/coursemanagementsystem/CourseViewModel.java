package edu.uk.le.coursemanagementsystem;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import java.util.List;
import edu.uk.le.coursemanagementsystem.model.Course;

public class CourseViewModel extends AndroidViewModel {
    private CourseRepo repo;
    private List<Course> allCourses;

    public CourseViewModel(Application application) {
        super(application);
        repo = new CourseRepo(application);
        allCourses = repo.getAllCourses();
    }

    List<Course> getAllCourses() {
        return allCourses;
    }

    public void insert(Course course) {
        repo.insert(course);
    }
}