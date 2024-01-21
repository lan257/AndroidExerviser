package com.example.androidandweb.O_solidObjects;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class video {
    int vid;
    @SerializedName("vname")
    String vName;
    @SerializedName("vintro")
    String vIntro="暂无";
    @SerializedName("vurl")
    String vUrl="暂无";
    @SerializedName("vcreate")
    String vCreate;
    @SerializedName("vchange")
    String vChange;
    int uid=1;
    String img;
    int ins;//权限
}

