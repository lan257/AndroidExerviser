package com.example.androidandweb.http;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.androidandweb.Q_sql.mySql;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressLint("NotConstructor")
public class PackageHttp {
    String vid;
    String data;

    public PackageHttp( String url, Object object) {
        mySql mySql = null;
        // 获取SharedPreferences对象，参数为文件名和访问模式
        SharedPreferences sharedPreferences = mySql.getSharedPreferences("LocalState");//本机状态文件
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
