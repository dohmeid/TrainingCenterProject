package com.example.trainingcenterproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList name,email,num;
    CustomAdapter(Context context,ArrayList num,ArrayList name,ArrayList email){
        this.context = context;
        this.name = name;
        this.email = email;
        this.num = num;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(String.valueOf(num.get(position)));
        holder.name.setText(String.valueOf(name.get(position)));
        holder.email.setText(String.valueOf(email.get(position)));

    }

    @Override
    public int getItemCount() {
        return num.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id,name,email;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.autonum);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
        }
    }
}
