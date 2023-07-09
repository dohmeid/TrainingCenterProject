package com.example.trainingcenterproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter4 extends RecyclerView.Adapter<CustomAdapter4.MyViewHolder>{
    private Context context;
    private ArrayList c_num,c_title,c_date,c_sts,c_venue,c_ins;
    CustomAdapter4(Context context,ArrayList c_num,ArrayList c_title,ArrayList c_date,
                   ArrayList c_sts,ArrayList c_venue,ArrayList c_ins){
        this.context = context;
        this.c_num = c_num;
        this.c_title = c_title;
        this.c_date = c_date;
        this.c_sts = c_sts;
        this.c_venue = c_venue;
        this.c_ins = c_ins;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.historybar,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter4.MyViewHolder holder, int position) {
        holder.c_num.setText("CourseNo. "+String.valueOf(c_num.get(position)));
        holder.c_title.setText("Course Title:"+String.valueOf(c_title.get(position)));
        holder.c_date.setText("Date."+String.valueOf(c_date.get(position)));
        holder.c_sts.setText("Time:"+String.valueOf(c_sts.get(position)));
        holder.c_venue.setText("Venue:"+String.valueOf(c_venue.get(position)));
        holder.c_ins.setText("Instructor Name:"+String.valueOf(c_ins.get(position)));

    }

    @Override
    public int getItemCount() {
        return c_num.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView c_num,c_title,c_date,c_sts,c_venue,c_ins;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            c_num = itemView.findViewById(R.id.c_num);
            c_title = itemView.findViewById(R.id.c_title);
            c_date = itemView.findViewById(R.id.c_date);
            c_sts = itemView.findViewById(R.id.c_sts);
            c_venue = itemView.findViewById(R.id.c_venue);
            c_ins = itemView.findViewById(R.id.c_ins);
        }
    }
}
