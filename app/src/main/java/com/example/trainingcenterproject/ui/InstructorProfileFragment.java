package com.example.trainingcenterproject.ui;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.trainingcenterproject.DataBaseHelper;
import com.example.trainingcenterproject.Instructor;
import com.example.trainingcenterproject.R;

import java.util.ArrayList;

public class InstructorProfileFragment extends Fragment {

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            // Inflate the layout for this fragment
            View root = inflater.inflate(R.layout.fragment_instructor_profile, container, false);

            TextView name = (TextView) root.findViewById(R.id.textView_name);
            TextView mail = (TextView) root.findViewById(R.id.textView_email);
            TextView number = (TextView) root.findViewById(R.id.textView_number);
            TextView address = (TextView) root.findViewById(R.id.textView_address);
            TextView degree = (TextView) root.findViewById(R.id.textView_degree);
            TextView specialization = (TextView) root.findViewById(R.id.textView_specialization);
            ImageView img = (ImageView) root.findViewById(R.id.imageView_photo);


            // Retrieve the data from the arguments
            Bundle arguments = getArguments();
            String email = "";
            if (arguments != null && arguments.containsKey("mail")) {
                email = arguments.getString("mail");
            }

            Instructor user = null;
            if(email != "") {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                user = dataBaseHelper.getInstructorData(email);
            }
            else {
                user = new Instructor("name1", "name2", "mail", "pass", null, "number", "address", "spec", "degree", new ArrayList<>());
                //Instructor user = (Instructor) arguments.getParcelable("Instructor"); // Retrieve the user object as Serializable
            }

            if (user != null) {
                name.setText(user.getFirstName() + " " + user.getSecondName());
                mail.setText(user.getEmail());
                number.setText(user.getMobile_number());
                address.setText(user.getAddress());
                degree.setText(user.getDegree());
                specialization.setText(user.getSpecialization());
                if (user.getPhoto() != null) {
                    byte[] img2 = user.getPhoto();
                    Bitmap imageBitmap = BitmapFactory.decodeByteArray(img2, 0, img2.length); // Convert the image bytes back to a Bitmap
                    img.setImageBitmap(imageBitmap);
                }
            }
            return root;
        }
        catch (Exception e) {
            Log.e(TAG, "onCreateView", e);
            throw e;
        }

    }

}