package com.example.trainingcenterproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class profileFragment extends Fragment {
    RecyclerView rec;
    ArrayList<String> usr_name,usr_email,address,mob,spe,deg;
    ArrayList<byte[]> usr_ph;
    DataBaseHelper dataBaseHelper;
    CustomAdapter3 customAdapter = new CustomAdapter3();
    ByteArrayOutputStream objectByteArrayOutputStream ;
    private byte[] imageInBytes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button tr = view.findViewById(R.id.Trainees);
        Button ins = view.findViewById(R.id.Instructors);
        rec = view.findViewById(R.id.recyclerPRF);
        dataBaseHelper = new DataBaseHelper(getContext());
        usr_name= new ArrayList<>();
        usr_email = new ArrayList<>();
        usr_ph = new ArrayList<>();

        address= new ArrayList<>();
        mob = new ArrayList<>();
        spe = new ArrayList<>();
        deg = new ArrayList<>();


        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr_name.clear();
                usr_email.clear();
                usr_ph.clear();
                address.clear();
                mob.clear();
                spe.clear();
                deg.clear();

                // Notify the adapter of the data set change
                customAdapter.notifyDataSetChanged();

                SQLiteDatabase MyDatabase = dataBaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("NAME1", "issa");
                contentValues.put("NAME2", "mohammad");
                contentValues.put("EMAIL", "isa@gmail.com");
                contentValues.put("PASSWORD", "isIsam12");
                // Convert the photo file to a Bitmap
                Bitmap imageToStoreBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.im1);
                objectByteArrayOutputStream = new ByteArrayOutputStream();
                imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);
                imageInBytes = objectByteArrayOutputStream.toByteArray();
                contentValues.put("PHOTO", imageInBytes);
                contentValues.put("MOBILE_NUM", "123456789");
                contentValues.put("ADDRESS", "ramallah");
                MyDatabase.insert("TRAINEES", null, contentValues);
                displayTrainee();
                customAdapter = new CustomAdapter3(getContext(),usr_name,usr_email,usr_ph,address,mob,null,null);
                rec.setAdapter(customAdapter);
                rec.setLayoutManager(new LinearLayoutManager(getContext()));


            }
        });
        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr_name.clear();
                usr_email.clear();
                usr_ph.clear();
                address.clear();
                mob.clear();
                spe.clear();
                deg.clear();

                // Notify the adapter of the data set change
                customAdapter.notifyDataSetChanged();
                //displayInstructor();
                //customAdapter = new CustomAdapter3(getContext(),usr_name,usr_email,usr_ph,address,mob,spe,deg);
                Bitmap imageToStoreBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.im4);
                objectByteArrayOutputStream = new ByteArrayOutputStream();
                imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);
                imageInBytes = objectByteArrayOutputStream.toByteArray();
                usr_name.add("lena ahmad");
                usr_email.add("lana@gmail.com");

                usr_ph.add(imageInBytes);
                address.add("nablus");
                mob.add("0597123481");
                spe.add("Computer Engineering");
                deg.add("PhD");
                customAdapter = new CustomAdapter3(getContext(),usr_name,usr_email,usr_ph,address,mob,spe,deg);

                rec.setAdapter(customAdapter);
                rec.setLayoutManager(new LinearLayoutManager(getContext()));


            }
        });
    }
    void displayTrainee(){
        Cursor cursor = dataBaseHelper.getAllTrainees();
        if(cursor.getCount() == 0){
            Toast.makeText(getActivity(),"No data.",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                usr_email.add(cursor.getString(0));
                usr_name.add(cursor.getString(1)+cursor.getString(2));
                usr_ph.add(cursor.getBlob(4));
                mob.add(cursor.getString(5));
                address.add(cursor.getString(6));
            }
        }
    }
    void displayInstructor(){
        Cursor cursor = dataBaseHelper.getAllInstructors();
        if(cursor.getCount() == 0){
            Toast.makeText(getActivity(),"No data.",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                usr_email.add(cursor.getString(0));
                usr_name.add(cursor.getString(1)+cursor.getString(2));
                usr_ph.add(cursor.getBlob(4));
                mob.add(cursor.getString(5));
                address.add(cursor.getString(6));
              //  spe.add(cursor.getString(7));
               // deg.add(cursor.getString(8));

            }
        }
    }


}
