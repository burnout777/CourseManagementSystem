package edu.uk.le.coursemanagementsystem.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.uk.le.coursemanagementsystem.model.Enrollment;

@Dao
public interface EnrollmentDao {

    @Insert
    void insert(Enrollment enrollment);

    @Delete
    void deleteEnrollment(Enrollment enrollment);

    @Query("SELECT * FROM enrollment")
    LiveData<List<Enrollment>> getAllEnrollments();

    @Query("SELECT * FROM enrollment WHERE course_id = :courseId")
    List<Enrollment> getStudentsInCourse(long courseId);

    @Query("SELECT EXISTS(SELECT 1 FROM enrollment WHERE course_id = :courseId AND student_id = :studentId)")
    boolean isStudentEnrolled(long courseId, long studentId);

    @Query("DELETE FROM enrollment WHERE student_id = :studentId AND course_id = :courseId")
    void deleteStudentFromCourse(long studentId, long courseId);
}