package edu.uk.le.coursemanagementsystem;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import edu.uk.le.coursemanagementsystem.dao.CourseDao;
import edu.uk.le.coursemanagementsystem.dao.EnrollmentDao;
import edu.uk.le.coursemanagementsystem.dao.StudentDao;
import edu.uk.le.coursemanagementsystem.model.Course;
import edu.uk.le.coursemanagementsystem.model.Enrollment;
import edu.uk.le.coursemanagementsystem.model.Student;

@Database(entities = {Student.class, Course.class, Enrollment.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract StudentDao studentDao();
    public abstract EnrollmentDao enrollmentDao();

    public abstract CourseDao courseDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "course_management_db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }


}

