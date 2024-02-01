package com.example.androidandweb.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.androidandweb.O_solidObjects.simpleObjects.aCon;
import com.example.androidandweb.R;
import com.example.androidandweb.http.PackageHttp;

import java.util.List;
import java.util.Objects;

public class aConAd extends ArrayAdapter<aCon> {
    private int re;

    private Context context;
    int changeId;

    public aConAd (Context con, int textViewResourceId, List<aCon> aConList ,int changeId){
        super(con,textViewResourceId,aConList);
        re=textViewResourceId;
        context=con;
        this.changeId=changeId;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        aCon ac = getItem(position);
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(getContext()).inflate(re,parent,false);
        TextView text=view.findViewById(R.id.text);
        ImageView img=view.findViewById(R.id.img);
        if (position==changeId){
            //实现text的android:background="@color/s112"
            text.setBackgroundColor(ContextCompat.getColor(context, R.color.s111));

        }
        if (ac.getType() == 1) {
            if (!Objects.equals(ac.getCon(), "")) {
                text.setText(ac.getCon());
            }
        } else {
            img.setVisibility(View.VISIBLE);
            text.setVisibility(View.GONE);
            if (ac.getUri()!=null){
                img.setImageURI(ac.getUri());
            }else {
                Glide.with(context).load(new PackageHttp().toImgUrl(ac.getCon())).into(img);
            }
        }
        return view;
    }
}
