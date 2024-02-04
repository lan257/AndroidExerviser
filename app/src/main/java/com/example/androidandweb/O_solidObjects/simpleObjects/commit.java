package com.example.androidandweb.O_solidObjects.simpleObjects;

import com.example.androidandweb.O_solidObjects.user;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class commit {
    user u;
    int cid;
    int aid;
    int uid;
    String com;
    String img="";
    int love;
    int replyNum;
    String time;
    String reply;
    public List<reply> GReply(){
        return new Gson().fromJson(reply, new TypeToken<List<reply>>(){}.getType());
    }
}
