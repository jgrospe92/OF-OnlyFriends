package com.example.myapplication;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class notificationViewHolder extends RecyclerView.ViewHolder {

    CircleImageView image;
    TextView imageName;
    LinearLayout parentLayout;

    public notificationViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imageprofile);
        imageName = itemView.findViewById(R.id.noti);
        parentLayout = itemView.findViewById(R.id.parentid);
    }
}
