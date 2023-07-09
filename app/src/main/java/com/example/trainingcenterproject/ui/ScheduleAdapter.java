package com.example.trainingcenterproject.ui;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingcenterproject.Course;
import com.example.trainingcenterproject.DataBaseHelper;
import com.example.trainingcenterproject.R;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>{

    private Context context;
    private ArrayList<Course> courseList;

    ScheduleAdapter(Context context,ArrayList<Course> courseList) {
        this.context =  context;
        this.courseList = courseList;
    }

    public ScheduleAdapter(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.className.setText(course.getTitle() + " class");

        DataBaseHelper db = new DataBaseHelper(context);
        String schedule = db.getCourseSchedule(course.getTitle());

        String[] str = schedule.split(",");
        holder.day.setText(str[0]);
        holder.time.setText(str[1]);

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
