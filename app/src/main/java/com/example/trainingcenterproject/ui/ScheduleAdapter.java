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

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>{

    private ArrayList<Course> courseList;

    public ScheduleAdapter(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public ScheduleAdapter.ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleAdapter.ScheduleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ScheduleViewHolder holder, int position) {
        Course course = courseList.get(position);

        // Bind the course data to the views in the ViewHolder
        String schedule = course.getSchedule();
        String[] str = schedule.split(",");
        holder.day.setText(str[0]);
        holder.time.setText(str[1]);
        holder.className.setText(course.getTitle() + " class");

        // ... Bind other views
        // Set any click listeners or additional logic here if needed
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {

        TextView day;
        TextView time;
        TextView className;
        // ... Declare other views

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);

            day = itemView.findViewById(R.id.TextViewDay);
            time = itemView.findViewById(R.id.TextViewTime);
            className = itemView.findViewById(R.id.TextViewClassName);
            // ... Initialize other views
        }
    }


}
