package edu.uk.le.coursemanagementsystem;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.uk.le.coursemanagementsystem.dao.EnrollmentDao;
import edu.uk.le.coursemanagementsystem.model.Enrollment;


public class EnrollmentRepo {

    private EnrollmentDao enrollmentDao;

    private LiveData<List<Enrollment>> allEnrollments;

    EnrollmentRepo(Application application) {
        AppDB db = AppDB.getDatabase(application);
        enrollmentDao = db.enrollmentDao();
        allEnrollments = enrollmentDao.getAllEnrollments();
    }

    LiveData<List<Enrollment>> getAllEnrollments() {return allEnrollments;}

    void insert(Enrollment enrollment) {
        AppDB.databaseWriteExecutor.execute(() -> {
            enrollmentDao.insert(enrollment);
        });
    }
}
