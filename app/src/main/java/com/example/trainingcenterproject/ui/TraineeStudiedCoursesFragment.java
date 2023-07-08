package com.example.trainingcenterproject.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainingcenterproject.DataBaseHelper;
import com.example.trainingcenterproject.R;

import java.util.Objects;

public class TraineeStudiedCoursesFragment extends Fragment {

    LinearLayout completedCourses;
    DataBaseHelper dataBaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_trainee_studied_courses, container, false);

        completedCourses = root.findViewById(R.id.coursesLayout);
        // Retrieve the data from the arguments
//        Bundle arguments = getArguments();
//        String mail = "";
//        if (arguments != null && arguments.containsKey("email")) {
//            mail = arguments.getString("email");
//        }
        SharedPreferences sharedPreferences = this.requireActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        String mail = sharedPreferences.getString("email", "");
        Toast.makeText(getActivity(), "email " + mail, Toast.LENGTH_SHORT).show();

        try {
            dataBaseHelper = new DataBaseHelper(getContext());
        } catch (Exception e){
            e.printStackTrace();
        }

//        dataBaseHelper.insertCompletedCourse("Java", mail, 1);
//        dataBaseHelper.insertCompletedCourse("C", mail, 2);
        Cursor allCoursesCursor = dataBaseHelper.getCompletedCourses(mail);
        completedCourses.removeAllViews();

        if(allCoursesCursor != null) {
            while (allCoursesCursor.moveToNext()) {
                TextView textView = new TextView(getActivity());
                textView.setHeight(150);
                textView.setTextSize(26);
                textView.setPadding(10, 10, 10, 10);
                textView.setText(allCoursesCursor.getString(0) + "\t\t" + allCoursesCursor.getString(1));
//                textView.setText( "Id: "+ allCoursesCursor.getString(0) +
//                        "\nTitle: "+ allCoursesCursor.getString(1) +
//                        "\nTopic: "+ allCoursesCursor.getString(2) +
//                        "\nPrerequisites: "+ allCoursesCursor.getString(3) +
//                        "\nInstructor: "+ allCoursesCursor.getString(4) +
//                        "\nDeadline: "+ allCoursesCursor.getString(5) +
//                        "\nStart Date: "+ allCoursesCursor.getString(6) +
//                        "\nSchedule: "+ allCoursesCursor.getString(7) +
//                        "\nVenue: "+ allCoursesCursor.getString(8) + "\n\n" );
                completedCourses.addView(textView);

            }
        }

        return root;
    }
}