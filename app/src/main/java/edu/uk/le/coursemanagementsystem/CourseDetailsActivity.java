package edu.uk.le.coursemanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import edu.uk.le.coursemanagementsystem.model.Course;
import edu.uk.le.coursemanagementsystem.model.Enrollment;
import edu.uk.le.coursemanagementsystem.model.Student;

public class CourseDetailsActivity extends AppCompatActivity implements StudentAdapter.OnStudentClickListener {

    private TextView tvCourseCode, tvCourseName, tvLecturerName;
    private RecyclerView rvStudentList;
    private StudentAdapter adapter;
    private List<Student> studentList = new ArrayList<>();

    private long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // course ID from intent
        courseId = getIntent().getLongExtra("course_id", -1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        Button addStudentButton = findViewById(R.id.btnAddStudentToCourse);
        addStudentButton.setOnClickListener(view -> {
            Intent intent = new Intent(CourseDetailsActivity.this, AddStudentActivity.class);
            intent.putExtra("COURSE_ID", ((int) courseId));
            startActivity(intent);
        });

        // Initialize views
        tvCourseCode = findViewById(R.id.tvCourseCode);
        tvCourseName = findViewById(R.id.tvCourseName);
        tvLecturerName = findViewById(R.id.tvLecturerName);
        rvStudentList = findViewById(R.id.rvStudentList);

        if (courseId == -1) {
            Toast.makeText(this, "Error: Course not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set up RecyclerView
        rvStudentList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(studentList, this);
        rvStudentList.setAdapter(adapter);

        StudentViewModel studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);

        studentViewModel.getStudentsForCourse(courseId).observe(this, students -> {
            adapter.setStudents(students);
        });

        // Load the course details and the  enrolled students
        loadCourseDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCourseDetails(); // Refresh data when returning to this activity
    }

    @Override
    public void onStudentClick(Student student) {
        // dialog with options to edit or remove student - number 7
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Student Options")
                .setItems(new String[]{"View Details", "Edit", "Remove from Course"}, (dialog, which) -> {
                    switch (which) {
                        case 0: // View Details
                            Intent detailsIntent = new Intent(CourseDetailsActivity.this, StudentDetailsActivity.class);
                            detailsIntent.putExtra("STUDENT_ID", student.getStudentId());
                            startActivity(detailsIntent);
                            break;
                        case 1: // Edit
                            Intent editIntent = new Intent(CourseDetailsActivity.this, EditStudentActivity.class);
                            editIntent.putExtra("STUDENT_ID", student.getStudentId());
                            startActivity(editIntent);
                            break;
                        case 2: // Removal from Course
                            removeStudentFromCourse(student);
                            break;
                    }
                })
                .show();
    }

    private void removeStudentFromCourse(Student student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove Student")
                .setMessage("Are you sure you want to remove " + student.getName() + " from this course?")
                .setPositiveButton("Remove", (dialog, which) -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        // Delete enrollment
                        AppDB db = AppDB.getDatabase(this);
                        db.enrollmentDao().deleteStudentFromCourse(student.getStudentId(), courseId);

                        runOnUiThread(() -> {
                            Toast.makeText(this, "Student removed from course", Toast.LENGTH_SHORT).show();
                            loadCourseDetails(); // refresh the list
                        });
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void loadCourseDetails() {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Get course details from database
            AppDB db = AppDB.getDatabase(this);
            Course course = db.courseDao().getCourseById(courseId);

            // getting enrolled students
            List<Enrollment> enrollments = db.enrollmentDao().getStudentsInCourse(courseId);
            List<Student> students = new ArrayList<>();

            for (Enrollment enrollment : enrollments) {
                Student student = db.studentDao().getStudentById(enrollment.getStudentId());
                if (student != null) {
                    students.add(student);
                }
            }

            runOnUiThread(() -> {
                if (course != null) {
                    tvCourseCode.setText(course.getCourseCode());
                    tvCourseName.setText(course.getCourseName());
                    tvLecturerName.setText(course.getLecturerName());
                } else {
                    Toast.makeText(this, "Error: Course not found in database", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                studentList.clear();
                studentList.addAll(students);
                adapter.notifyDataSetChanged();

                if (students.isEmpty()) {
                    Toast.makeText(this, "No students enrolled in this course", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}