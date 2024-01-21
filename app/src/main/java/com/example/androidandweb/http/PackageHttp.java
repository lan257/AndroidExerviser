package com.example.androidandweb.http;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.androidandweb.O_solidObjects.user;
import com.example.androidandweb.Q_sql.mySql;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@SuppressLint("NotConstructor")
public class PackageHttp {
    String vid;
    String data;
    String jwt;

    public PackageHttp( String url, Object object) {
        mySql mySql = null;
        // 获取SharedPreferences对象，参数为文件名和访问模式
        SharedPreferences sharedPreferences = mySql.getSharedPreferences("LocalState");//本机状态文件
        String Vid=sharedPreferences.getString("vid", "none");
        jwt=sharedPreferences.getString("token", "none");
        this.vid = Vid+"/aaw"+url;
        Log.i("请求url:",this.vid);
        this.data=toJson(object);
    }

    public String toImgUrl(String url){
        SharedPreferences sharedPreferences = mySql.getSharedPreferences("LocalState");//本机状态文件
        String Vid=sharedPreferences.getString("vid", "none");
        return Vid+ url;
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
