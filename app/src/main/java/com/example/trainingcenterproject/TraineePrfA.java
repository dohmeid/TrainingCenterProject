package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TraineePrfA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_prf);
        ImageView im = findViewById(R.id.pt);

        TextView name = findViewById(R.id.usr_nametprf);
        TextView email = findViewById(R.id.use_emailprf);
        TextView address = findViewById(R.id.t_ad);
        TextView mobile = findViewById(R.id.t_num);

        Intent intent = getIntent();
        String uname = intent.getStringExtra("user_name");
        String umail = intent.getStringExtra("user_email");
        String uadd = intent.getStringExtra("user_address");
        String unum = intent.getStringExtra("user_number");

        byte[] photo = intent.getByteArrayExtra("user_image");
        Bitmap photoBitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        im.setImageBitmap(photoBitmap);
        name.setText(uname);
        email.setText(umail);
        address.setText(uadd);
        mobile.setText(unum);



    }


}