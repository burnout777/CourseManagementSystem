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
    long insertStudent(Student student);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Student student);

    @Query("SELECT * FROM student WHERE student_id IN (SELECT student_id FROM enrollment WHERE course_id = :courseId)")
    LiveData<List<Student>> getStudentsForCourse(long courseId);

    @Query("SELECT * FROM student")
    LiveData<List<Student>> getAllStudents();

    @Query("SELECT student_id FROM student WHERE user_name = :user_name")
    Student getStudentIDByUserName(long user_name);

    @Query("SELECT * FROM student WHERE student_id = :id")
    Student getStudentById(long id);



    @Query("SELECT * FROM student WHERE user_name = :userName")
    Student getStudentByUserName(String userName);
}