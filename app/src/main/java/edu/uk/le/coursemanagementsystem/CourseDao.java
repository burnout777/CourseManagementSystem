package edu.uk.le.coursemanagementsystem;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

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
}