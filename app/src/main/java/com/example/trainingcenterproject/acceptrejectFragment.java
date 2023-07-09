package com.example.trainingcenterproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class acceptrejectFragment extends Fragment {
    RecyclerView rec;
    ArrayList<String> application_num,course_id,student_email;
    DataBaseHelper dataBaseHelper ;
    CustomAdapter2 customAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.acceptreject_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* dataBaseHelper = new DataBaseHelper(getContext());
        SQLiteDatabase MyDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",2);
        contentValues.put("COURSE_ID",1);
        contentValues.put("USER_EMAIL", "isa@gmail.com");
        MyDatabase.insert("PENDING_COURSES", null, contentValues);*/


        rec = view.findViewById(R.id.recyclerACC);
        dataBaseHelper = new DataBaseHelper(getContext());
        application_num = new ArrayList<>();
        course_id = new ArrayList<>();
        student_email = new ArrayList<>();
        displayData();
        customAdapter = new CustomAdapter2(getContext(),application_num,course_id,student_email);
        rec.setAdapter(customAdapter);
        customAdapter.setOnItemClickListener(new CustomAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                application_num.remove(position);
                student_email.remove(position);
                customAdapter.notifyItemRemoved(position);
            }
        });
        rec.setLayoutManager(new LinearLayoutManager(getContext()));

    }
    @SuppressLint("Range")
    void displayData(){
        Cursor cursor = dataBaseHelper.getPendingCourses();
        if(cursor.getCount() == 0){
            Toast.makeText(getActivity(),"No data.",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                application_num.add(cursor.getString(cursor.getColumnIndex("ID")));
                course_id.add(cursor.getString(cursor.getColumnIndex("COURSE_ID")));
                student_email.add(cursor.getString(cursor.getColumnIndex("USER_EMAIL")));

            }
        }
    }


}
