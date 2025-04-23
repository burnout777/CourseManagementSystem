package edu.uk.le.coursemanagementsystem.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.uk.le.coursemanagementsystem.model.Course;
import edu.uk.le.coursemanagementsystem.model.Student;

@Dao
public interface CourseDao {

    @Insert
    void insertCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query("SELECT * FROM course")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM course WHERE course_id = :id")
    Course getCourseById(long id);

    @Query("DELETE FROM course")
    void deleteAll();
}