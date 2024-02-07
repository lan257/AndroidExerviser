package com.example.androidandweb.http;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.androidandweb.Q_sql.mySql;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@SuppressLint("NotConstructor")
public class PackageHttp {
    SharedPreferences sharedPreferences = mySql.getSharedPreferences("LocalState");//本机状态文件
    String vid;
    String data;
    String jwt;

    public PackageHttp(String url) {
        String Vid = sharedPreferences.getString("vid", "none");
        jwt = sharedPreferences.getString("token", "none");
        this.vid = Vid + "/aaw" + url;
    }

    public PackageHttp(String url, Object object) {
        // 获取SharedPreferences对象，参数为文件名和访问模式
        String Vid = sharedPreferences.getString("vid", "none");
        jwt = sharedPreferences.getString("token", "none");
        this.vid = Vid + "/aaw" + url;
        Log.i("请求url:", this.vid);
        this.data = toJson(object);
    }

    public String changeFileName(String filePath, String pro, String path) {
        File file = new File(filePath);
        String result = "/" + path + "/" + pro + file.getName();
        return result;
    }

    public String toImgUrl(String url) {
        String Vid = sharedPreferences.getString("vid", "none");
        return Vid +"/uploads" + url;
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

    public static String formatTime(String inputTime) {
        String timeType = "yyyy-MM-dd'T'HH:mm:ss";
        try {
            return toTime(inputTime, timeType);
        } catch (DateTimeParseException e) {
            timeType="yyyy-MM-dd' 'HH:mm:ss";
            return toTime(inputTime, timeType);
        }
    }


    private static String toTime(String inputTime, String timeType) {

        // 定义输入时间的格式
        DateTimeFormatter inputFormatter = new DateTimeFormatterBuilder()
                .appendPattern(timeType)
                .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)  // 允许小数位的范围是0到9
                .toFormatter();
        // 解析输入时间字符串
        LocalDateTime dateTime = LocalDateTime.parse(inputTime, inputFormatter);

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 如果时间为当天
        if (dateTime.toLocalDate().isEqual(now.toLocalDate())) {
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");
            return dateTime.format(outputFormatter);
        }
        // 如果时间距今小于一周
        else if (dateTime.isAfter(now.minus(7, ChronoUnit.DAYS))) {
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("E HH:mm");
            return dateTime.format(outputFormatter);
        }
        // 如果时间为当年
        else if (dateTime.getYear() == now.getYear()) {
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
            return dateTime.format(outputFormatter);
        }
        // 其他情况
        else {
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return dateTime.format(outputFormatter);
        }
    }
}