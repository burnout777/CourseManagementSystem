package edu.uk.le.coursemanagementsystem;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.uk.le.coursemanagementsystem.dao.StudentDao;
import edu.uk.le.coursemanagementsystem.model.Student;

public class StudentRepo {
    private StudentDao studentDao;

    private LiveData<List<Student>> allStudents;

    StudentRepo(Application application) {
        AppDB db = AppDB.getDatabase(application);
        studentDao = db.studentDao();
        allStudents = studentDao.getAllStudents();
    }

    LiveData<List<Student>> getStudentsForCourse(long courseId) {
        return studentDao.getStudentsForCourse(courseId);
    }
    LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }
    void insert(Student student) {
        AppDB.databaseWriteExecutor.execute(() -> {
            studentDao.insertStudent(student);
        });
    }

}
