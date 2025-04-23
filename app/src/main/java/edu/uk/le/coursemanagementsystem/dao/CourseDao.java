package edu.uk.le.coursemanagementsystem.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import edu.uk.le.coursemanagementsystem.model.Course;

@Dao
public interface CourseDao {

    @Insert
    void insertCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query("SELECT * FROM course")
    List<Course> getAllCourses();

    @Query("SELECT * FROM course WHERE course_id = :id")
    Course getCourseById(long id);

    @Query("DELETE FROM course")
    void deleteAll();

    @Query("DELETE FROM course WHERE course_id = :courseId")
    void deleteCourseById(long courseId);

    List<Course> getCoursesForStudent(int studentId);

}