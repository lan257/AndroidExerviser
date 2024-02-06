package com.example.androidandweb.O_solidObjects.simpleObjects;

import android.net.Uri;

import com.example.androidandweb.Q_sql.mySql;
import com.example.androidandweb.http.PackageHttp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class aCon {

    int type;//1：文段，2：图片，3：其他
    String con;//内容/服务器图片
    Uri uri;//本地图片地址

    public aCon(int type, String con) {
        this.type = type;
        this.con = con;
    }


    public void conChange(){
        if (type==2){
            String pro=mySql.getSharedPreferences("LocalState").getInt("uid", 1)+"---";
            con=new PackageHttp().changeFileName(con,pro,"ActImg");
            uri=null;
        }
    }
}
