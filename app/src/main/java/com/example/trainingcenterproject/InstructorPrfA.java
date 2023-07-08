package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class InstructorPrfA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_prf);
        ImageView im = findViewById(R.id.pi);
        TextView name = findViewById(R.id.usr_nameiprf);
        TextView email = findViewById(R.id.use_emailiprf);
        TextView address = findViewById(R.id.i_ad);
        TextView mobile = findViewById(R.id.i_num);
        TextView d = findViewById(R.id.i_deg);
        TextView s = findViewById(R.id.i_sp);

        Intent intent = getIntent();
        String uname = intent.getStringExtra("user_name");
        String umail = intent.getStringExtra("user_email");
        String uadd = intent.getStringExtra("user_address");
        String unum = intent.getStringExtra("user_number");
        String usp = intent.getStringExtra("user_sp");
        String udeg = intent.getStringExtra("user_degree");

        byte[] photo = intent.getByteArrayExtra("user_image");
        Bitmap photoBitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        im.setImageBitmap(photoBitmap);
        name.setText(uname);
        email.setText(umail);
        address.setText(uadd);
        mobile.setText(unum);
        d.setText(udeg);
        s.setText(usp);


    }
}