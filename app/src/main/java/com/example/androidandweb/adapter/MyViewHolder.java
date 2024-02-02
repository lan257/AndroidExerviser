package com.example.androidandweb.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidandweb.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView;
    public TextView userNicknameTextView;
    public TextView comNumTextView;
    public TextView loveTextView;
//    public LinearLayout titleImg;
public ImageView titleImg;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title);
        userNicknameTextView = itemView.findViewById(R.id.userNickname);
//        comNumTextView = itemView.findViewById(R.id.comNum);
//        loveTextView = itemView.findViewById(R.id.love);
        titleImg=itemView.findViewById(R.id.imgTest);
    }
}