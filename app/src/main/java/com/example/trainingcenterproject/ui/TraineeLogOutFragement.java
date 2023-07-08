package com.example.trainingcenterproject.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingcenterproject.MainActivity;
import com.example.trainingcenterproject.R;

public class TraineeLogOutFragement extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trainee_log_out_fragement, container, false);
    }

    public void onStart() {
        super.onStart();
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
        try {
            finalize();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}