package edu.uk.le.coursemanagementsystem;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void insertStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Query("SELECT * FROM student")
    List<Student> getAllStudents();

    @Query("SELECT * FROM student WHERE student_id = :id")
    Student getStudentById(long id);
}