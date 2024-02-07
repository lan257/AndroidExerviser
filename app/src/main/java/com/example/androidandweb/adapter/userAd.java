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
import com.example.androidandweb.O_solidObjects.user;
import com.example.androidandweb.R;
import com.example.androidandweb.http.PackageHttp;

import java.util.List;

public class userAd extends ArrayAdapter<user> {
    private int re;

    private Context context;

    public userAd (Context con, int textViewResourceId, List<user> userList){
        super(con,textViewResourceId,userList);
        re=textViewResourceId;
        context=con;
    }
    public View getView(int position , View coverView, ViewGroup parent){
        user u=getItem(position);
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(getContext()).inflate(re,parent,false);
        ImageView img=view.findViewById(R.id.list_user_img);
        TextView name=view.findViewById(R.id.list_user_nickName);

        name.setText(u.getNickname());
        PackageHttp packageHttp = new PackageHttp();
        String url=packageHttp.toImgUrl(u.getImg())  ;
        Glide.with(context).load(url).circleCrop().into(img);
        return view;
    }
}
