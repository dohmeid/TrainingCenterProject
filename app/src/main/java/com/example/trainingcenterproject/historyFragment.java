package com.example.trainingcenterproject;

import android.annotation.SuppressLint;
import android.database.Cursor;
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

public class historyFragment extends Fragment {
    RecyclerView rec;
    Button search;
    ArrayList<String> c_num,c_title,c_date,c_sts,c_venue,c_ins;

    EditText symbol;
    DataBaseHelper dataBaseHelper;
    CustomAdapter4 customAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.history_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rec = view.findViewById(R.id.hrecycler);
        search = view.findViewById(R.id.hbutton);
        symbol = view.findViewById(R.id.hcoursenum);
        dataBaseHelper = new DataBaseHelper(getContext());
        c_num = new ArrayList<>();
        c_title = new ArrayList<>();
        c_date = new ArrayList<>();
        c_ins = new ArrayList<>();
        c_sts = new ArrayList<>();
        c_venue = new ArrayList<>();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayData();
                customAdapter = new CustomAdapter4(getContext(),c_num,c_title,c_date,c_sts,c_venue,c_ins);
                rec.setAdapter(customAdapter);
                rec.setLayoutManager(new LinearLayoutManager(getContext()));


            }
        });
    }
    @SuppressLint("Range")
    void displayData(){
        Cursor cursor = dataBaseHelper.getOfferingHistory(symbol.getText().toString());
        if(cursor.getCount() == 0){
            Toast.makeText(getActivity(),"No data.",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                c_num.add(cursor.getString(cursor.getColumnIndex("COURSE_NUM")));
                c_title.add(cursor.getString(cursor.getColumnIndex("TITLE")));
                c_date.add(cursor.getString(cursor.getColumnIndex("START_DATE")));
                c_sts.add(cursor.getString(cursor.getColumnIndex("SCHEDULE")));
                c_venue.add(cursor.getString(cursor.getColumnIndex("VENUE")));
                c_ins.add(cursor.getString(cursor.getColumnIndex("INSTRUCTOR_NAME")));

            }
        }
    }
}
