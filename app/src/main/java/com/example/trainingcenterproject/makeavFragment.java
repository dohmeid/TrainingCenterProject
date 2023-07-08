package com.example.trainingcenterproject;

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

public class makeavFragment extends Fragment {
    EditText symbol,inst,deadline,start,sch,venue;
    Button makeAv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.makeav_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        symbol = view.findViewById(R.id.avacoursenum);
        inst = view.findViewById(R.id.avaInstrName);
        deadline = view.findViewById(R.id.avadeadline);
        start = view.findViewById(R.id.avastart);
        sch = view.findViewById(R.id.avaschedule);
        venue = view.findViewById(R.id.avavenue);
        makeAv = view.findViewById(R.id.avabutton);

        makeAv.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                if(!symbol.getText().toString().isEmpty()&&!inst.getText().toString().isEmpty()
                        &&!deadline.getText().toString().isEmpty()&&!start.getText().toString().isEmpty()
                        &&!sch.getText().toString().isEmpty()&&!makeAv.getText().toString().isEmpty()){
                    Course newCourse =new Course(
                            symbol.getText().toString(),
                            inst.getText().toString(),
                            deadline.getText().toString(),
                            start.getText().toString(),
                            sch.getText().toString(),
                            venue.getText().toString());
                    DataBaseHelper dataBaseHelper =new DataBaseHelper(getContext());
                    Course c = dataBaseHelper.searchCourse(symbol.getText().toString());
                    if(c == null){
                        Toast.makeText(getActivity(),"Course does not exist",Toast.LENGTH_SHORT).show();
                    }else{
                        dataBaseHelper.insertToAvaCourses(newCourse);
                        symbol.setText("");
                        inst.setText("");
                        deadline.setText("");
                        start.setText("");
                        sch.setText("");
                        venue.setText("");
                    }


                }
                else{
                    Toast.makeText(getActivity(),"please fill all required fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
