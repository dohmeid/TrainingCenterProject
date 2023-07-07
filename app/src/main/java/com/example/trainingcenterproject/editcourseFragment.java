package com.example.trainingcenterproject;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import androidx.fragment.app.Fragment;

import java.util.List;

public class editcourseFragment extends Fragment {
    ImageView photo;
    EditText symbol,newsym;
    EditText title;
    EditText main_topic;
    EditText pre;
    Button search;
    static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageFilePath;
    private Bitmap imageToStore;
    Course searched_course;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.editcourse_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        symbol = view.findViewById(R.id.editcourssymbol);
        title = view.findViewById(R.id.editcourseTitle);
        main_topic = view.findViewById(R.id.editcourseMainTopics);
        pre = view.findViewById(R.id.editcoursePrerequisites);
        photo = view.findViewById(R.id.editcoursePhoto);
        Button imgBtn = view.findViewById(R.id.editimbut);
        Button edit = view.findViewById(R.id.editbutton);
        search = view.findViewById(R.id.editsearch);
        newsym = view.findViewById(R.id.editcoursenewsym);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper =new DataBaseHelper(getContext());
                searched_course = dataBaseHelper.searchCourse(symbol.getText().toString());
                if(searched_course == null){
                    Toast.makeText(getActivity(),"Course does not exist",Toast.LENGTH_SHORT).show();
                }else {
                    title.setText(searched_course.getTitle());
                    newsym.setText(searched_course.getSymbol());
                    main_topic.setText(searched_course.getMain_topics());
                    pre.setText(searched_course.getPrereq());
                    photo.setImageBitmap(searched_course.getPhoto());
                }
            }
        });



        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!title.getText().toString().isEmpty()&&!symbol.getText().toString().isEmpty()
                        &&!main_topic.getText().toString().isEmpty()&&!pre.getText().toString().isEmpty()){
                    Course newCourse =new Course(0,
                            title.getText().toString(),
                            newsym.getText().toString(),
                            main_topic.getText().toString(),
                            pre.getText().toString(),
                            ((BitmapDrawable) photo.getDrawable()).getBitmap());
                    DataBaseHelper dataBaseHelper =new DataBaseHelper(getContext());
                    dataBaseHelper.updateCourse(newCourse,symbol.getText().toString());
                    symbol.setText("");
                    newsym.setText("");
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
