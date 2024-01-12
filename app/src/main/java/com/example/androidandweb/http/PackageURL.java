package com.example.androidandweb.http;

public class PackageURL {
    String vid;

    public PackageURL(String vid) {
        this.vid = vid+"/aaw";
    }

    public String getUrl(String url){
        return vid+url;
    }
}
