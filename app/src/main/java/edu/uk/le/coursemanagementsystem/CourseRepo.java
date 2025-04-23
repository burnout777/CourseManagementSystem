package edu.uk.le.coursemanagementsystem;

import android.app.Application;
import java.util.List;
import edu.uk.le.coursemanagementsystem.dao.CourseDao;
import edu.uk.le.coursemanagementsystem.model.Course;

public class CourseRepo {
    private CourseDao courseDao;

    CourseRepo(Application application) {
        AppDB db = AppDB.getDatabase(application);
        courseDao = db.courseDao();
    }

    List<Course> getAllCourses() {
        return courseDao.getAllCourses();
    }

    void insert(Course course) {
        AppDB.databaseWriteExecutor.execute(() -> {
            courseDao.insertCourse(course);
        });
    }
}