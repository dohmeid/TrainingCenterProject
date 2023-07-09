package com.example.trainingcenterproject.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingcenterproject.Course;
import com.example.trainingcenterproject.DataBaseHelper;
import com.example.trainingcenterproject.Instructor;
import com.example.trainingcenterproject.R;

import java.util.ArrayList;

public class InstructorScheduleFragment extends Fragment {

    ArrayList<Course> courses;
    private RecyclerView recyclerView;
    private ScheduleAdapter scheduleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_instructor_schedule, container, false);


        // Retrieve the data from the arguments
        Bundle arguments = getArguments();
        String email = "";
        if (arguments != null && arguments.containsKey("mail")) {
            email = arguments.getString("mail");
        }
        if(email != "") {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
            Instructor user = dataBaseHelper.getInstructorData(email);
            String name = user.getFirstName() + " " + user.getSecondName();
            courses = dataBaseHelper.getInstructorCourses(email);
        }
        else{
            courses = null;
        }

        if(courses != null){
            // Initialize RecyclerView
            recyclerView = root.findViewById(R.id.recyclerView2);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            // Create and set up the adapter
            scheduleAdapter = new ScheduleAdapter(getContext(),courses);
            recyclerView.setAdapter(scheduleAdapter);
        }

        return root;
    }
}