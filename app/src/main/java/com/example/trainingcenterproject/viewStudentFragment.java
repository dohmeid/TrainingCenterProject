package com.example.trainingcenterproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class viewStudentFragment extends Fragment {
    RecyclerView rec;
    Button search;
    ArrayList<String> id,student_name,student_email;
    EditText symbol;
    DataBaseHelper dataBaseHelper;
    CustomAdapter customAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.viewstudent_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBaseHelper = new DataBaseHelper(getContext());
        SQLiteDatabase MyDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",1);
        contentValues.put("COURSE_ID",3);
        contentValues.put("STUDENT_NAME","mahdi");
        contentValues.put("USER_EMAIL", "Sara@gmail.com");
        MyDatabase.insert("REGISTERED_COURSES", null, contentValues);
        rec = view.findViewById(R.id.recycler);
        search = view.findViewById(R.id.stbutton);
        symbol = view.findViewById(R.id.stcoursenum);
        dataBaseHelper = new DataBaseHelper(getContext());
        student_name = new ArrayList<>();
        student_email = new ArrayList<>();
        id = new ArrayList<>();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayData();
                customAdapter = new CustomAdapter(getContext(),id,student_name,student_email);
                rec.setAdapter(customAdapter);
                rec.setLayoutManager(new LinearLayoutManager(getContext()));


            }
        });

    }
    @SuppressLint("Range")
    void displayData(){
        Cursor cursor = dataBaseHelper.getCourseStudents(symbol.getText().toString());
        if(cursor.getCount() == 0){
            Toast.makeText(getActivity(),"No data.",Toast.LENGTH_SHORT).show();
        }else{
            int i =1;
            while (cursor.moveToNext()){
                id.add(""+i);
                student_name.add(cursor.getString(cursor.getColumnIndex("STUDENT_NAME")));
                student_email.add(cursor.getString(cursor.getColumnIndex("USER_EMAIL")));
                i++;

            }
        }
    }

}
