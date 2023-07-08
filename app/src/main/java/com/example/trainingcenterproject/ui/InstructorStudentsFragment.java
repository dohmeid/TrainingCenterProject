package com.example.trainingcenterproject.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingcenterproject.Course;
import com.example.trainingcenterproject.DataBaseHelper;
import com.example.trainingcenterproject.Instructor;
import com.example.trainingcenterproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InstructorStudentsFragment extends Fragment {

    String email = "";
    ArrayList<Course> courses;
    HashMap<String, ArrayList<String>> map ;

    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;

      public InstructorStudentsFragment() {
        // Required empty public constructor
    }

    public static InstructorStudentsFragment newInstance(String str) {
        InstructorStudentsFragment fragment = new InstructorStudentsFragment();
        Bundle args = new Bundle();
        args.putString("mail", str);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString("mail");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_instructor_students, container, false);

        // Retrieve the data from the arguments
       /* Bundle arguments = getArguments();
        String email = "";
        if (arguments != null && arguments.containsKey("mail")) {
            email = arguments.getString("mail");
        }*/

        if(email != "") {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
            Instructor user = dataBaseHelper.getInstructorData(email);
            String name = user.getFirstName() + " " + user.getSecondName();
            //dataBaseHelper.insertCourse(c1);
            //dataBaseHelper.insertCourse(c2);

            /*  dataBaseHelper.insertRegisteredCourse(1,"java","Doha Hmeid");
            dataBaseHelper.insertRegisteredCourse(2,"java","Samar Rami");
            dataBaseHelper.insertRegisteredCourse(3,"java","Zeed Ali");
            dataBaseHelper.insertRegisteredCourse(4,"java","Riad Sameh");
            dataBaseHelper.insertRegisteredCourse(5,"c","Remah Alosh");
            dataBaseHelper.insertRegisteredCourse(6,"c","Diana Rami");
            dataBaseHelper.insertRegisteredCourse(7,"c","Ahmad Salem");
            */

            courses = dataBaseHelper.getInstructorCourses(email);
            map = new HashMap<>();
            for(int i=0; i<courses.size() ; i++){
                Course c = courses.get(i);
                String title = c.getTitle();
                ArrayList<String> students  = dataBaseHelper.getCourseStudents2(title);
                map.put(title,students);
            }
        }
        else{
            courses = null;
        }

        if(courses != null){
            // Initialize RecyclerView
            recyclerView = root.findViewById(R.id.recyclerView3);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            // Create and set up the adapter
            studentAdapter = new StudentAdapter(map);
            recyclerView.setAdapter(studentAdapter);
        }



        return root;
    }
}