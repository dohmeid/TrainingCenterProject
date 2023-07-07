package com.example.trainingcenterproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CustomAdapter3 extends RecyclerView.Adapter<CustomAdapter3.MyViewHolder>{
    private Context context;
    private ArrayList usr_name,usr_email,address,mob,spe,deg;
    private ArrayList<byte[]>ph;


    CustomAdapter3(){

    }
    CustomAdapter3(Context context,ArrayList usr_name,ArrayList usr_email,ArrayList ph,ArrayList address,ArrayList mob,
                   ArrayList spe,ArrayList deg ){
        this.context = context;
        this.usr_name = usr_name;
        this.usr_email = usr_email;
        this.ph = ph;
        this.address = address;
        this.mob = mob;
        this.spe = spe;
        this.deg = deg;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.profilebar,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Get the values at the specified position
        String userName = String.valueOf(usr_name.get(position));
        String userEmail = String.valueOf(usr_email.get(position));
        byte[] imageBytes = ph.get(position);
        String mobile = String.valueOf(mob.get(position));
        String add = String.valueOf(address.get(position));
        // Pass the values to the onClick method using final variables
        final String finalUserName = userName;
        final String finalUserEmail = userEmail;
        final String finalMobile = mobile;
        final String finalAddress = add;
        final byte[] finalImageBytes = imageBytes;


        if(this.deg!=null){
            String sp = String.valueOf(spe.get(position));
            String dg = String.valueOf(deg.get(position));
            final String finalSp = sp;
            final String finalDg = dg;
            holder.prof.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, InstructorPrfA.class);
                    intent.putExtra("user_name",  finalUserName);
                    intent.putExtra("user_email", finalUserEmail);
                    intent.putExtra("user_image", finalImageBytes);
                    intent.putExtra("user_number", finalMobile);
                    intent.putExtra("user_address", finalAddress);
                    intent.putExtra("user_sp", finalSp);
                    intent.putExtra("user_degree",finalDg);

                    context.startActivity(intent);
                }
            });
        }else{
            holder.prof.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TraineePrfA.class);
                    intent.putExtra("user_name",  finalUserName);
                    intent.putExtra("user_email", finalUserEmail);
                    intent.putExtra("user_image", finalImageBytes);
                    intent.putExtra("user_number", finalMobile);
                    intent.putExtra("user_address", finalAddress);
                    context.startActivity(intent);
                }
            });

        }



        // Set the values to the views in the ViewHolder
        holder.usr_name.setText(userName);
        holder.usr_email.setText(userEmail);
        holder.ph.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));


    }

    @Override
    public int getItemCount() {
        return usr_name.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView usr_name,usr_email;
        ImageView prof,ph;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            usr_name = itemView.findViewById(R.id.usr_namep);
            usr_email= itemView.findViewById(R.id.use_emailp);
            ph = itemView.findViewById(R.id.imageProfilep);
            prof = itemView.findViewById(R.id.profp);
        }
    }
}
