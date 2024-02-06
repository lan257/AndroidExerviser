package com.example.androidandweb.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidandweb.O_solidObjects.simpleObjects.msg;
import com.example.androidandweb.R;
import com.example.androidandweb.http.PackageHttp;

import java.util.List;

public class msgAd extends ArrayAdapter<msg> {
    private int re;

    private Context context;
    int Me ;
    String uImg, mImg;
    public msgAd (Context con, int textViewResourceId, List<msg> videoList, int Me ,String uImg,String mImg){
        super(con,textViewResourceId,videoList);
        re=textViewResourceId;
        context=con;
        this.Me=Me;
        this.uImg=uImg;
        this.mImg=mImg;
    }
    public View getView(int position , View coverView, ViewGroup parent ){
        msg m=getItem(position);
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(getContext()).inflate(re,parent,false);
        TextView text=view.findViewById(R.id.msg);
        TextView msgTime=view.findViewById(R.id.msgTime);
        ImageView MeImg=view.findViewById(R.id.meImger);
        ImageView YouImg=view.findViewById(R.id.youImger);
        text.setText(m.getText());
        msgTime.setText(new PackageHttp().formatTime(m.getTime()));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.gravity = Gravity.LEFT; // 或者使用 Gravity.LEFT
        if (m.getUserIs()!=Me){
            MeImg.setVisibility(View.GONE);
            Glide.with(context).load(this.uImg).circleCrop().into(YouImg);
            text.setLayoutParams(layoutParams);
            msgTime.setLayoutParams(layoutParams);
        }else {
            YouImg.setVisibility(View.GONE);
            Glide.with(context).load(this.mImg).circleCrop().into(MeImg);
        }
        return view;
    }
}