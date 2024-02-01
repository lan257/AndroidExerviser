package com.example.androidandweb.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidandweb.O_solidObjects.VSmail;
import com.example.androidandweb.O_solidObjects.video;
import com.example.androidandweb.R;
import com.example.androidandweb.http.PackageHttp;

import java.util.List;

public class videoAd extends ArrayAdapter<video> {
    private int re;

    private Context context;

    public videoAd (Context con, int textViewResourceId, List<video> videoList){
        super(con,textViewResourceId,videoList);
        re=textViewResourceId;
        context=con;
    }
    public View getView(int position , View coverView, ViewGroup parent){
        video v=getItem(position);
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(getContext()).inflate(re,parent,false);
        ImageView img=view.findViewById(R.id.videoImger);
        TextView name=view.findViewById(R.id.videoName);
        TextView intro=view.findViewById(R.id.videoIntro);
        intro.setText(v.getVIntro());
        name.setText(v.getVName());
        PackageHttp packageHttp = new PackageHttp();
        String url=packageHttp.toImgUrl(v.getImg())  ;

        Log.i("图片", url);
        Glide.with(context).load(url).circleCrop().into(img);
//        email.setText(u.getEmail());
        return view;
    }
}
