package edu.uk.le.coursemanagementsystem;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.uk.le.coursemanagementsystem.dao.CourseDao;
import edu.uk.le.coursemanagementsystem.dao.EnrollmentDao;
import edu.uk.le.coursemanagementsystem.dao.StudentDao;
import edu.uk.le.coursemanagementsystem.model.Course;
import edu.uk.le.coursemanagementsystem.model.Enrollment;
import edu.uk.le.coursemanagementsystem.model.Student;

@Database(entities = {Course.class, Student.class, Enrollment.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    public abstract CourseDao courseDao();

    public abstract StudentDao studentDao();

    public abstract EnrollmentDao enrollmentDao();

    private static volatile AppDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDB.class, "app_db")
                            .addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                CourseDao dao = INSTANCE.courseDao();
                dao.deleteAll();
                Course course = new Course();
                course.setCourseCode("1");
                course.setCourseName("Death and Suffering");
                course.setLecturerName("paul");
                dao.insertCourse(course);
            });
        }
    };
}

