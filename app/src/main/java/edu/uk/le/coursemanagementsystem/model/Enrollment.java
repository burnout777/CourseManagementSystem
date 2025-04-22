package edu.uk.le.coursemanagementsystem.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "enrollment", primaryKeys = {"course_id", "student_id"})
public class Enrollment {

    @ColumnInfo(name = "course_id")
    private long courseId;

    @ColumnInfo(name = "student_id")
    private long studentId;

    // Getters and Setters
    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }
}
