package com.example.trainingcenterproject.ui;

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
import java.util.HashMap;
import java.util.Map;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder>{

    HashMap<String, ArrayList<String>> map ;
    ArrayList<HashMap.Entry<String, ArrayList<String>>> entryList;

    public StudentAdapter(HashMap<String, ArrayList<String>> map) {
        this.map = map;
        // Create an intermediate List to maintain the order
        this.entryList = new ArrayList<>(map.entrySet());
    }

    @NonNull
    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentAdapter.StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder holder, int position) {
       // Course course = courseList.get(position);
        HashMap.Entry<String, ArrayList<String>> entry = entryList.get(position);
        String keyTitle = entry.getKey();
        ArrayList<String> value = entry.getValue();

        String sList = "";
        for(String str : value){
            sList += str+"\n";
        }
        holder.courseName.setText(keyTitle);
        holder.studentsList.setText(sList);

    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView courseName,studentsList;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.TextViewCourseName);
            studentsList = itemView.findViewById(R.id.TextViewStudents);
            // ... Initialize other views
        }
    }
}
