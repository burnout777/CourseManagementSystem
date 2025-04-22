package edu.uk.le.coursemanagementsystem;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EnrollmentDao {

    @Insert
    void insertEnrollment(Enrollment enrollment);

    @Delete
    void deleteEnrollment(Enrollment enrollment);

    @Query("SELECT * FROM enrollment WHERE course_id = :courseId")
    List<Enrollment> getStudentsInCourse(long courseId);

    @Query("SELECT * FROM enrollment WHERE student_id = :studentId")
    List<Enrollment> getCoursesForStudent(long studentId);
}