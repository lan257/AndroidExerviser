package com.example.androidandweb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidandweb.O_solidObjects.VSmail;
import com.example.androidandweb.R;

import java.util.List;

public class VSmailAd  extends ArrayAdapter<VSmail>{
        private int re;

        private Context context;
    VSmail v;

        public VSmailAd (Context con, int textViewResourceId, List<VSmail> VSmailList){
            super(con,textViewResourceId,VSmailList);
            re=textViewResourceId;
            context=con;
        }
        public View getView(int position , View coverView, ViewGroup parent){
            v=getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(re,parent,false);
            TextView name=view.findViewById(R.id.SVideoName);
            name.setText(v.getName());
            TextView intro=view.findViewById(R.id.SVideoIntro);
            intro.setText(v.getAlso());
//            ImageButton love=view.findViewById(R.id.AdLove);
//            love.setOnClickListener(this);
            return view;
        }

//    @Override
//    public void onClick(View v) {
//        if (v.getId()==R.id.AdLove){
//            Toast.makeText(context, v+"点赞", Toast.LENGTH_SHORT).show();
//        }
//    }
}