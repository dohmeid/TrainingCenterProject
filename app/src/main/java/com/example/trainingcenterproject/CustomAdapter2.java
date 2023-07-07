package com.example.trainingcenterproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.MyViewHolder>{
    private Context context;
    private ArrayList num,email;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener clickListener){
        listener = clickListener;
    }

    CustomAdapter2(Context context,ArrayList num,ArrayList email){
        this.context = context;
        this.num = num;
        this.email= email;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.applications,parent,false);
        return new MyViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter2.MyViewHolder holder, int position) {
        holder.id.setText("Application No."+String.valueOf(num.get(position)));
        holder.email.setText(String.valueOf(email.get(position)));

    }

    @Override
    public int getItemCount() {
        return num.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id,email;
        ImageView delete;
        public MyViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            id = itemView.findViewById(R.id.acreview0);
            email= itemView.findViewById(R.id.email);
            delete = itemView.findViewById(R.id.del);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());

                }
            });
            itemView.findViewById(R.id.acceptbutton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String mEmail = email.getText().toString();
                    String mSubject = "Application Accepted";
                    String mMessage =" We are happy to inform you that your application has been accepted";
                    javaMailApi javaMailAPI = new javaMailApi(context, mEmail, mSubject, mMessage);
                    javaMailAPI.execute();

                }
            });

            itemView.findViewById(R.id.rejectbutton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String mEmail = email.getText().toString();
                    String mSubject = "Application Rejected";
                    String mMessage =" We regret to inform you that your application has been rejected";
                    javaMailApi javaMailAPI = new javaMailApi(context, mEmail, mSubject, mMessage);
                    javaMailAPI.execute();

                }
            });
        }
    }
}
