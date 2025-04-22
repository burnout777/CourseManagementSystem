package edu.uk.le.coursemanagementsystem;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class, Student.class, Enrollment.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract CourseDao courseDao();
    public abstract StudentDao studentDao();
    public abstract EnrollmentDao enrollmentDao();

    private static AppDB INSTANCE;

    public static synchronized AppDB getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDB.class,
                    "course_management_db"
            ).fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}