package com.example.trainingcenterproject.ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trainingcenterproject.Course;
import com.example.trainingcenterproject.DataBaseHelper;
import com.example.trainingcenterproject.R;


public class CoursesHistoryFragment extends Fragment {

    LinearLayout coursesLayout;
    DataBaseHelper dataBaseHelper;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_courses_history, container, false);

        coursesLayout = root.findViewById(R.id.coursesLayout);

        try {
            dataBaseHelper = new DataBaseHelper(getContext());
        } catch (Exception e){
            e.printStackTrace();
        }

//        Course c1 = new Course("Java", "OOP", "C,Digital");
//        Course c2 = new Course("C", "Strings, Arrays", "Digital");
//        dataBaseHelper.insertCourse(c1);
//        dataBaseHelper.insertCourse(c2);
        Cursor allCoursesCursor = dataBaseHelper.getCoursesByLatest();
        coursesLayout.removeAllViews();

        if(allCoursesCursor != null) {
            while (allCoursesCursor.moveToNext()) {
                TextView textView = new TextView(getActivity());
                textView.setHeight(150);
                textView.setTextSize(18);
                textView.setPadding(10, 10, 10, 10);
                textView.setText(allCoursesCursor.getString(0) + "\t" + allCoursesCursor.getString(2) + "\t" + allCoursesCursor.getString(1));

                coursesLayout.addView(textView);

            }
        }
        return root;
    }
}