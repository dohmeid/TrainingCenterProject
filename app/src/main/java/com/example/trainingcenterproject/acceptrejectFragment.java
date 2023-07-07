package com.example.trainingcenterproject;

import android.database.Cursor;
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

public class acceptrejectFragment extends Fragment {
    RecyclerView rec;
    ArrayList<String> application_num,student_email;
    DataBaseHelper dataBaseHelper;
    CustomAdapter2 customAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.acceptreject_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rec = view.findViewById(R.id.recyclerACC);
        dataBaseHelper = new DataBaseHelper(getContext());
        application_num = new ArrayList<>();
        student_email = new ArrayList<>();
        displayData();
        customAdapter = new CustomAdapter2(getContext(),application_num,student_email);
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
    void displayData(){
        application_num.add("1");
        application_num.add("2");
        student_email.add("romaneh.sarah@gmail.com");
        student_email.add("saraissamr@gmail.com");

       /* Cursor cursor = dataBaseHelper.getCourseStudents(symbol.getText().toString());
        if(cursor.getCount() == 0){
            Toast.makeText(getActivity(),"No data.",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                application_num.add(cursor.getString(0));
                student_email.add(cursor.getString(2));

            }
        }*/
    }


}
