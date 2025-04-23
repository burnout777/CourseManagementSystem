package edu.uk.le.coursemanagementsystem;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.uk.le.coursemanagementsystem.dao.CourseDao;
import edu.uk.le.coursemanagementsystem.model.Course;

public class CourseRepo {
    private CourseDao courseDao;
    private LiveData<List<Course>> allCourses;

    CourseRepo(Application application) {
        AppDB db = AppDB.getDatabase(application);
        courseDao = db.courseDao();
        allCourses = courseDao.getAllCourses();
    }

    LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    void insert(Course course) {
        AppDB.databaseWriteExecutor.execute(() -> {
            courseDao.insertCourse(course);
        });
    }
}