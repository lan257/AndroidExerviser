package com.example.androidandweb.Q_sql;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class mySql extends Application {
    private static SharedPreferences sharedPreferences;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        // 默认文件名为 "default_prefs_file"
        sharedPreferences = getSharedPreferences("default_prefs_file", Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPreferences(String fileName) {
        // 使用传入的文件名创建或获取SharedPreferences实例
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public static Context getContext() {
        return context;
    }
}
