package com.example.androidandweb;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

// 在您的 Application 类中
public class MyApp extends Application {
    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("shared_prefs_file", Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
