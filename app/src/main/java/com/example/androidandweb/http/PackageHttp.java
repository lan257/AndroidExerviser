package com.example.androidandweb.http;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.androidandweb.MyApp;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressLint("NotConstructor")
public class PackageHttp {
    String vid;
    String data;

    public PackageHttp( String url, Object object) {

        SharedPreferences sharedPreferences = MyApp.getSharedPreferences();
        String Vid=sharedPreferences.getString("vid", "none");
        this.vid = Vid+"/aaw"+url;
        Log.i("",this.vid);
        this.data=toJson(object);
    }



    String toJson(Object object){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
