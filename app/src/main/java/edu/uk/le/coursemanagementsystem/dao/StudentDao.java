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
public interface StudentDao {

    @Insert
    void insertStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Query("SELECT * FROM student")
    LiveData<List<Student>> getAllStudents();

    @Query("SELECT * FROM student WHERE student_id = :id")
    Student getStudentById(long id);
}