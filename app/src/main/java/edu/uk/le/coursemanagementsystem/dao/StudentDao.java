package edu.uk.le.coursemanagementsystem.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.uk.le.coursemanagementsystem.model.Student;

@Dao
public interface StudentDao {

    @Insert
    void insertStudent(Student student);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Student student);

    @Query("SELECT * FROM student")
    LiveData<List<Student>> getAllStudents();

    @Query("SELECT * FROM student WHERE student_id = :id")
    Student getStudentById(long id);

    @Query("SELECT * FROM students WHERE studentId = :id")
    Student getStudentById(int id);


    Student getStudentByUserName(String userName);
}