package com.example.trainingcenterproject;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;

public class addcourseFragment extends Fragment {
    ImageView photo;
    EditText symbol;
    EditText title;
    EditText main_topic;
    EditText pre;

    static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageFilePath;
    private Bitmap imageToStore;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.addcourse_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        symbol = view.findViewById(R.id.courseSymbol);
        title = view.findViewById(R.id.courseTitle);
        main_topic = view.findViewById(R.id.courseMainTopics);
        pre = view.findViewById(R.id.coursePrerequisites);
        photo = view.findViewById(R.id.coursePhoto);
        int oldPhoto= photo.getDrawable().hashCode();
        Button imgBtn = view.findViewById(R.id.imbut);
        Button add = view.findViewById(R.id.addbutton);



        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override

           public void onClick(View v) {
                int newPhoto =photo.getDrawable().hashCode();
                if(newPhoto!=oldPhoto&&!title.getText().toString().isEmpty()&&!symbol.getText().toString().isEmpty()
                &&!main_topic.getText().toString().isEmpty()&&!pre.getText().toString().isEmpty()){
                    Course newCourse =new Course(0,
                            title.getText().toString(),
                            symbol.getText().toString(),
                            main_topic.getText().toString(),
                            pre.getText().toString(),
                            imageToStore);
                    DataBaseHelper dataBaseHelper =new DataBaseHelper(getContext());
                    dataBaseHelper.insertCourse(newCourse);
                    symbol.setText("");
                    title.setText("");
                    pre.setText("");
                    main_topic.setText("");
                    photo.setImageResource(R.drawable.ic_image);

                }
                else{
                    Toast.makeText(getActivity(),"please fill all required fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void chooseImage() {
        try{
            Intent objIntent = new Intent();
            objIntent.setType("image/*");
            objIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(objIntent, "Select Photo"), PICK_IMAGE_REQUEST);
        }catch (Exception e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            super.onActivityResult(requestCode,resultCode,data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!=null) {
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap( requireActivity().getContentResolver(), imageFilePath);
                photo.setImageBitmap(imageToStore);

            }
        }catch (Exception e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

}
