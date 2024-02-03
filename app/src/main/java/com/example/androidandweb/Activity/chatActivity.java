package com.example.androidandweb.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidandweb.O_solidObjects.chat;
import com.example.androidandweb.O_solidObjects.simpleObjects.Result;
import com.example.androidandweb.O_solidObjects.simpleObjects.msg;
import com.example.androidandweb.O_solidObjects.user;
import com.example.androidandweb.R;
import com.example.androidandweb.adapter.msgAd;
import com.example.androidandweb.http.PackageHttp;
import com.example.androidandweb.http.postJwt;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.util.List;

public class chatActivity extends AppCompatActivity implements View.OnClickListener {

    user you,me=new user();
    String YouImg,MeImg;
    chat chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        goStart();
        Button sent=findViewById(R.id.sentChatMsg);
        sent.setOnClickListener(this);
        Button again=findViewById(R.id.chatAgain);
        again.setOnClickListener(this);
    }

    private void getUser() {
        String url = "/getUserJwt";
        byte s = 0;
        postJwt.sendPostRequest(url, s, result -> {
            // 处理返回的Result对象
            if (result != null) {
                Log.i("个人信息1",result.iu+result.msg+result.data);
                if(result.iu==1){
                    String user= new Gson().toJson(result.data);
                    me= new Gson().fromJson(user, user.class);
                }}
            youShow();
            showChat();});}

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.sentChatMsg){
            sent();
        }
        if (v.getId()==R.id.chatAgain){
           again();
    }}
 void again(){
    chat c=chat;
    postJwt.sendPostRequest("/selectChat", c, result -> {
        // 处理返回的Result对象
        if (result != null) {
            if(result.iu!=0){
                String chat1= new Gson().toJson(result.data);
                chat =new Gson().fromJson(chat1,chat.class);
                showChat();
            }}});

}
    private void sent() {
        EditText sentMsg=findViewById(R.id.chatMsg);
        String text=sentMsg.getText().toString();
        String time= LocalDateTime.now().toString();
        if (!text.equals("")){
            msg finMsh=new msg(me.getUid(),text,time);
            chat.SETFinMsg(finMsh);
            chat.addMsgData();
            chat c=chat;
            Log.i("添加记录",c+"");
            postJwt.sendPostRequest("/addMsg", c, result -> {
                // 处理返回的Result对象
                if (result != null) {
                    if(result.iu!=0){
                        showChat();
                        sentMsg.setText("");
                    }}});}}
    private void showChat() {
        List<msg> msgList=chat.getMsg();
        if (msgList.size()>45){
            msgList=msgList.subList(msgList.size()-40,msgList.size());
        }
        msgAd adapter=new msgAd(chatActivity.this, R.layout.s_chat_msg,msgList,me.getUid(),YouImg,MeImg);
        ListView listView=findViewById(R.id.chatShow);
        listView.setVisibility(View.VISIBLE);
        findViewById(R.id.load).setVisibility(View.GONE);
        listView.setAdapter(adapter);
        int lastItem = adapter.getCount() - 1;
        if (lastItem >= 0) {
            listView.setSelection(lastItem);
        }
    }

    private void goStart() {
        Intent getIntent=getIntent();
        int youId=getIntent.getIntExtra("you",1);
        user u=new user();
        u.setUid(youId);
        String url="/uid/selectUser";
        postJwt.sendPostRequest(url, u, new postJwt.OnResultListener() {
            public void onResult(Result result) {
                // 处理返回的Result对象
                if (result != null) {
                    if(result.iu!=0){
                        String user= new Gson().toJson(result.data);
//                        Type type = new TypeToken<user>(){}.getType();
                       you= new Gson().fromJson(user, user.class);
                    }}}});
        url="/selectChat";
        chat sChat=new chat(youId);
        postJwt.sendPostRequest(url, sChat, result -> {
            // 处理返回的Result对象
            if (result != null) {
                if(result.iu!=0){
                    String chat1= new Gson().toJson(result.data);
                    chat =new Gson().fromJson(chat1,chat.class);}
            }else {
                    Toast.makeText(chatActivity.this, "出错了", Toast.LENGTH_SHORT).show();
                }
            getUser();});}

    private void youShow() {
        ImageView youImg =findViewById(R.id.youImger);
        YouImg = new PackageHttp().toImgUrl(you.getImg());
        MeImg = new PackageHttp().toImgUrl(me.getImg());
        Glide.with(chatActivity.this).load(this.YouImg).circleCrop().into(youImg);
        TextView nickName=findViewById(R.id.user_nickname);
        nickName.setText(you.getNickname());
    }
}