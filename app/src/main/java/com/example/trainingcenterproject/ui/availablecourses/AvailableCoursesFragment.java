package com.example.trainingcenterproject.ui.availablecourses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trainingcenterproject.BR;
import com.example.trainingcenterproject.Course;
import com.example.trainingcenterproject.DataBaseHelper;
import com.example.trainingcenterproject.R;

import java.sql.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class AvailableCoursesFragment extends Fragment {

    LinearLayout coursesLayout;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        View root = inflater.inflate(R.layout.fragment_available_courses, container, false);

        coursesLayout = root.findViewById(R.id.availableCoursesLayout);

        // Retrieve the data from the arguments
//        Bundle arguments = getArguments();
//        String mail = "";
//        if (arguments != null && arguments.containsKey("email")) {
//            mail = arguments.getString("email");
//
//        }
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        String mail = sharedPreferences.getString("email", "");
        Toast.makeText(getActivity(), "email " + mail, Toast.LENGTH_SHORT).show();

        //@SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DataBaseHelper dataBaseHelper =new DataBaseHelper(getContext());
        Cursor allCoursesCurser = dataBaseHelper.getAvailableCourses();
        coursesLayout.removeAllViews();

        while (allCoursesCurser.moveToNext()){

//            LayoutInflater inflater2 = LayoutInflater.from(getActivity());
//            View includedLayout = inflater.inflate(R.layout.available_course_view, null);
            //ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_trainee_view_available_courses);
//            ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.available_course_view, coursesLayout, true);
//                Course course = null;
//                try {
//                    course = new Course(Integer.parseInt(allCoursesCurser.getString(0)), allCoursesCurser.getString(1), allCoursesCurser.getString(2),
//                            allCoursesCurser.getString(3), allCoursesCurser.getString(4), (Date) df.parseObject(allCoursesCurser.getString(5)),
//                            (Date) df.parseObject(allCoursesCurser.getString(6)), allCoursesCurser.getString(7), allCoursesCurser.getString(8));
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
//            binding.setVariable(BR.course, course);
//            binding.executePendingBindings();

            TextView c = new TextView(getActivity());
            c.setTextSize(22);

            String a = allCoursesCurser.getString(0);
            c.setText(allCoursesCurser.getString(0) + "\t" + allCoursesCurser.getString(1));

            coursesLayout.addView(c);
            TextView info = new TextView(getActivity());
            info.setTextSize(18);
            info.setPadding(10,5,5,5);
            info.setText("Topic: " + allCoursesCurser.getString(2) +
                    "\nPrerequisites: " + allCoursesCurser.getString(3) +
                    "\nInstructor: " + allCoursesCurser.getString(4)  +
                    "\nDeadline: " + allCoursesCurser.getString(5) +
                    "\nStart Date: " + allCoursesCurser.getString(6)  +
                    "\nSchedule: " + allCoursesCurser.getString(7) +
                    "\nVenue: " + allCoursesCurser.getString(8) + "\n");
            coursesLayout.addView(info);

//            coursesLayout.addView(includedLayout);
            //LinearLayout includedLayout = findViewById(R.id.available_course_view);
//          Button apply = includedLayout.findViewById(R.id.apply);

            Button apply = new Button(getActivity());
            apply.setText("Apply");

            String finalMail = mail;
            apply.setOnClickListener(view -> {      // need to check if the prerequisites are done

                Cursor course = dataBaseHelper.getAvailableCourse(a);
                course.moveToFirst();

                String[] prerequisites = course.getString(3).split(",");
                int flag1 = 1, flag2 = 1;
                for (String s : prerequisites) {
                    Toast.makeText(getActivity(), finalMail+" "+s, Toast.LENGTH_SHORT).show();
                    if(!Objects.equals(s, "")) {
                        if (!dataBaseHelper.checkCompletion(finalMail, s)) {
                            flag1 = 0;
                            break;
                        }
                    }
                }
                String[] schedule = course.getString(7).split(",");
                String days = schedule[0], time = schedule[1];
                String[] hour = time.split(":");

                Cursor availableCurser = dataBaseHelper.getAvailableCourses();
                availableCurser.moveToFirst();
                do {
                    if(!Objects.equals(availableCurser.getString(0), course.getString(0))) {
                        String[] schedule2 = availableCurser.getString(7).split(",");
                        String days2 = schedule2[0], time2 = schedule[1];
                        String[] hour2 = time.split(":");
                        if (Objects.equals(days2, days)) {
                            if (Objects.equals(hour2[0], hour[0]) ||  java.lang.Math.abs(Integer.parseInt(hour2[0]) - Integer.parseInt(hour[0])) < 1){
                                flag2 = 0;
                                break;
                            }
                        }
                    }
                } while (availableCurser.moveToNext());

                if(flag1 == 1 && flag2 ==1) {
                    dataBaseHelper.insertTraineeCourse(Integer.parseInt(allCoursesCurser.getString(0)), finalMail);
                } else if (flag1 == 0){
                    Toast.makeText(getActivity(), "You have not completed all prerequisites for this course!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "You have time conflict with other courses you are enrolled in!", Toast.LENGTH_SHORT).show();
                }
            });
            coursesLayout.addView(apply);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}