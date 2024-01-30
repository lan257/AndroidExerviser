package com.example.androidandweb.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.androidandweb.O_solidObjects.simpleObjects.Result;
import com.example.androidandweb.O_solidObjects.simpleObjects.chatList;
import com.example.androidandweb.O_solidObjects.user;
import com.example.androidandweb.R;
import com.example.androidandweb.adapter.chatListAd;
import com.example.androidandweb.http.postJwt;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class chatListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        goo();
    }

    private void goo() {
        String url = "/getChatList";
        byte s = 0;
        postJwt.sendPostRequest(url, s, new postJwt.OnResultListener() {
            public void onResult(Result result) {
                // 处理返回的Result对象
                if (result != null) {
                    Type type = new TypeToken<List<chatList>>(){}.getType();
                    List<chatList> chatList =new Gson().fromJson(new Gson().toJson(result.data), type);
                    Log.i("测试私聊列表",chatList.get(0).getMsg().getTime()+"");
                    chatListAd adapter=new chatListAd(chatListActivity.this, R.layout.chat_list_show,chatList);
                    ListView listView=findViewById(R.id.chatListShow);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            chatList cl= chatList.get(position);
                            //跳转chatActivity页面
                            Intent intent=new Intent(chatListActivity.this, chatActivity.class);
                            Log.i("你是谁？",""+cl);
                            intent.putExtra("you",cl.getUser().getUid());
                            startActivity(intent);
                        }
                    });
                    }}});
    }
}