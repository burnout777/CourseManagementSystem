package edu.uk.le.coursemanagementsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import edu.uk.le.coursemanagementsystem.R;
import edu.uk.le.coursemanagementsystem.model.Course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    List<Course> courseList;
    private OnCourseClickListener listener;

    public CourseAdapter(List<Course> courseList, OnCourseClickListener listener) {
        this.courseList = courseList;
        this.listener = listener;
    }

    public CourseAdapter(List<Course> enrolledCourses) {
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    public void setCourses(List<Course> courses) {
        this.courseList = courses;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.tvName.setText(course.getCourseName());
        holder.tvCode.setText(course.getCourseCode());
        holder.tvLecturer.setText(course.getLecturerName());

        // Click handling
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCourseClick(course);
            }
        });

        // Long click handling
        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onCourseLongClick(course);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCode, tvLecturer;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCourseName);
            tvCode = itemView.findViewById(R.id.tvCourseCode);
            tvLecturer = itemView.findViewById(R.id.tvLecturerName);
        }
    }

    public interface OnCourseClickListener {
        void onCourseClick(Course course);
        void onCourseLongClick(Course course);
    }
}
