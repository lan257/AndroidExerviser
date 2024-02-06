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
import com.example.androidandweb.O_solidObjects.simpleObjects.chatList;
import com.example.androidandweb.R;
import com.example.androidandweb.http.PackageHttp;

import java.util.List;

public class chatListAd extends ArrayAdapter<chatList> {
    private int re;

    private Context context;

    public chatListAd(Context con, int textViewResourceId, List<chatList> chatListList){
        super(con,textViewResourceId,chatListList);
        re=textViewResourceId;
        context=con;
    }
    public View getView(int position , View coverView, ViewGroup parent){
        chatList cL=getItem(position);
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(getContext()).inflate(re,parent,false);
        ImageView img=view.findViewById(R.id.chatImager);
        TextView nickname=view.findViewById(R.id.nickName);
        TextView time=view.findViewById(R.id.msgTime);
        TextView text=view.findViewById(R.id.finMsg);
        String url=new PackageHttp().toImgUrl(cL.getUser().getImg());
        Log.i("图片？",cL+"");
        Glide.with(context).load(url).circleCrop().into(img);
        nickname.setText(cL.getUser().getNickname());
        String DataTime=new PackageHttp().formatTime(cL.getMsg().getTime());
        time.setText(DataTime);
        text.setText(cL.getMsg().getText());
        return view;
    }
}
