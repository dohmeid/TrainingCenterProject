package com.example.trainingcenterproject.ui.searchcourses;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trainingcenterproject.Course;
import com.example.trainingcenterproject.DataBaseHelper;
import com.example.trainingcenterproject.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchCoursesFragment extends Fragment {


    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        View root = inflater.inflate(R.layout.fragment_search_courses, container, false);
        EditText courseName;
        Button search;
        LinearLayout courseResult;

        courseName = root.findViewById(R.id.courseName);
        search = root.findViewById(R.id.searchBtn);
        courseResult = root.findViewById(R.id.resultsLayout);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        Date d1, d2;
//        try {
//            d1 = df.parse("2023-09-10"); // for example, today's date
//            d2 = df.parse("2023-10-01");
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }

//        Course c1 = new Course("DataBase", "SQL", "Java", "Basem", d1, d2, "MW,11:25", "Masri203");
//        Course c2 = new Course("AI", "SPF, Constraint", "DataBase", "Aziz", d1, d2, "TR,1:00", "Masri105");
//        dataBaseHelper.insertAvailableCourse(c1);
//        dataBaseHelper.insertAvailableCourse(c2);

        search.setOnClickListener(view -> {
            if (courseName.getText().toString().isEmpty()) {
                courseName.setError("Please insert course name.");
            } else {

                Cursor allCoursesCurser = dataBaseHelper.getAvailableCourses();
                courseResult.removeAllViews();

                if (allCoursesCurser == null) {
                    TextView textView = new TextView(getActivity());
                    textView.setText("No Courses Found!");
                    courseResult.addView(textView);
                } else {
                    while (allCoursesCurser.moveToNext()) {
                        if (allCoursesCurser.getString(1).contains(courseName.getText().toString())) {

                            TextView textView = new TextView(getActivity());
                            textView.setTextSize(22);
                            textView.setPadding(10,10,10,10);
                            textView.setText("Id: " + allCoursesCurser.getString(0) +
                                    "\nTitle: " + allCoursesCurser.getString(1) +
                                    "\nTopic: " + allCoursesCurser.getString(2) +
                                    "\nPrerequisites: " + allCoursesCurser.getString(3) +
                                    "\nInstructor: " + allCoursesCurser.getString(4) +
                                    "\nDeadline: " + allCoursesCurser.getString(5) +
                                    "\nStart Date: " + allCoursesCurser.getString(6) +
                                    "\nSchedule: " + allCoursesCurser.getString(7) +
                                    "\nVenue: " + allCoursesCurser.getString(8) + "\n\n");
                            courseResult.addView(textView);

                        }
                    }
                }
            }

        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}