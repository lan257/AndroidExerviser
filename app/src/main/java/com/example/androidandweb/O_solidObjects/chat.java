package com.example.androidandweb.O_solidObjects;

import com.example.androidandweb.O_solidObjects.simpleObjects.msg;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class chat {
    int chatId;
    int userA;
    int userB;
    String msgData;//聊天记录
    String finMsg;
    public chat(int userA, int userB) {
        this.userA = userA;
        this.userB = userB;
        createCid();
    }
    public void SETFinMsg(msg finMsg) {
        this.finMsg = new Gson().toJson(finMsg);
    }
    public msg getData() {
        return new Gson().fromJson(finMsg, new com.google.gson.reflect.TypeToken<msg>(){}.getType());
    }
    public chat(int userB) {
        this.userB = userB;
    }

    void createCid(){
        chatId=userA>userB ? Objects.hash(userA,userB):Objects.hash(userB,userA);
    }
    void toMsgData(List<msg> msgList){
        msgData=new Gson().toJson(msgList);
    }

    public List<msg> getMsg(){
        if (msgData!=null  && !msgData.isEmpty()){
            return new Gson().fromJson(msgData, new com.google.gson.reflect.TypeToken<List<msg>>(){}.getType());
        }
        return new ArrayList<>();
    }
    public void addMsgData(){
        toMsgData(addMsg(getMsg()));
    }
    List<msg> addMsg(List<msg> msgList){
        msgList.add(getData());
        return msgList;
    }
}
