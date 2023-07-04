package com.example.trainingcenterproject.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingcenterproject.Course;
import com.example.trainingcenterproject.R;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private ArrayList<Course> courseList;

    public CourseAdapter(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);

        // Bind the course data to the views in the ViewHolder
        holder.titleTextView.setText(course.getTitle());
        holder.numberTextView.setText(String.valueOf(course.getNumber()));
        // ... Bind other views

        // Set any click listeners or additional logic here if needed
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView numberTextView;
        // ... Declare other views

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            numberTextView = itemView.findViewById(R.id.numberTextView);
            // ... Initialize other views
        }
    }
}


