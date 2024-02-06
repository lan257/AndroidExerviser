package com.example.androidandweb.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.androidandweb.O_solidObjects.simpleObjects.Result;
import com.example.androidandweb.O_solidObjects.simpleObjects.chatList;
import com.example.androidandweb.R;
import com.example.androidandweb.adapter.chatListAd;
import com.example.androidandweb.http.postJwt;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class chatListActivity extends AppCompatActivity {
    List<chatList> chatListS;
    @Override
    protected void onResume() {
        super.onResume();
        // 在页面从后台返回前台时触发的操作
        // 例如，显示一个 Toast 消息
        goo();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        goo();
        Button ChatSelect=findViewById(R.id.chatSelect);
        ChatSelect.setOnClickListener(v -> chatSelect());
    }

    private void chatSelect() {
        EditText nickName=findViewById(R.id.chatListText);
        String name= nickName.getText().toString();
        List<chatList> chatListV = new LinkedList<>();
        for (chatList cl:chatListS
             ) {
            if (cl.toString().contains(name)){
                chatListV.add(cl);
            }
        }
        chatListShow(chatListV);
    }

    private void goo() {
        String url = "/getChatList";
        byte s = 0;
        postJwt.sendPostRequest(url, s, result -> {
            // 处理返回的Result对象
            if (result != null) {
                Type type = new TypeToken<List<chatList>>(){}.getType();
                chatListS =new Gson().fromJson(new Gson().toJson(result.data), type);
                chatListAd adapter=new chatListAd(chatListActivity.this, R.layout.s_chat_list_show,chatListS);
                ListView listView=findViewById(R.id.chatListShow);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener((parent, view, position, id) -> {

                    chatList cl= chatListS.get(position);
                    //跳转chatActivity页面
                    Intent intent=new Intent(chatListActivity.this, chatActivity.class);
                    intent.putExtra("you",cl.getUser().getUid());
                    startActivity(intent);});
                }});}

        private void chatListShow(List<chatList> chatListS) {
//        Log.i("测试私聊列表",chatList.get(0).getMsg().getTime()+"");
        chatListAd adapter=new chatListAd(chatListActivity.this, R.layout.s_chat_list_show,chatListS);
        ListView listView=findViewById(R.id.chatListShow);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {

            chatList cl= chatListS.get(position);
            //跳转chatActivity页面
            Intent intent=new Intent(chatListActivity.this, chatActivity.class);
            Log.i("你是谁？",""+cl);
            intent.putExtra("you",cl.getUser().getUid());
            startActivity(intent);
        });
    }
}