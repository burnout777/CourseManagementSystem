package edu.uk.le.coursemanagementsystem;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import edu.uk.le.coursemanagementsystem.model.Student;

public class StudentViewModel extends AndroidViewModel {
    private StudentRepo repo;
    private final LiveData<List<Student>> allStudents;

    public StudentViewModel(Application application) {
        super(application);
        repo = new StudentRepo(application);
        allStudents = repo.getAllStudents();
    }

    public LiveData<List<Student>> getStudentsForCourse(long courseId) {
        return repo.getStudentsForCourse(courseId);
    }



    LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    public void insert(Student student) {
        repo.insert(student);
    }
}