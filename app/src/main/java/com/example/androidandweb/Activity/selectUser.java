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
import android.widget.Toast;

import com.example.androidandweb.O_solidObjects.chat;
import com.example.androidandweb.O_solidObjects.simpleObjects.Result;
import com.example.androidandweb.O_solidObjects.user;
import com.example.androidandweb.R;
import com.example.androidandweb.adapter.userAd;
import com.example.androidandweb.http.postJwt;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class selectUser extends AppCompatActivity implements View.OnClickListener{

    private List<user> userList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        Button selectU=findViewById(R.id.AAWSelect);
        selectU.setOnClickListener(this);
    }
    public void onClick(View v){
        if(v.getId()==R.id.AAWSelect){
            Log.i("","触发点击事件");
            AAWS();
        }
    }
    //查询用户
    public void AAWS(){
        EditText nick=findViewById(R.id.SUText);
        String nickName;
        if(nick.getText().toString().equals("")){
            nickName="all";
        }else {
        nickName = nick.getText().toString();}
        user s=new user();
        s.setNickname(nickName);
        String url="/selectUser";
        Log.i("触发查询事件",s.toString());
        postJwt.sendPostRequest(url, s, new postJwt.OnResultListener() {
            public void onResult(Result result) {
                // 处理返回的Result对象
                if (result != null) {
                    if(result.iu!=0){
                        String List= new Gson().toJson(result.data);
                        Type type = new TypeToken<List<user>>(){}.getType();
                        List<user> userList = new Gson().fromJson(List, type);
                        // 现在你可以使用userList了
                        // ...


                        userAd adapter=new userAd(com.example.androidandweb.Activity.selectUser.this, R.layout.layout_user_show,userList);
                        ListView listView=findViewById(R.id.userShow);
                        listView.setAdapter(adapter);
                        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                                user u= userList.get(position);
                                //跳转chatActivity页面
                                Intent intent=new Intent(selectUser.this, chatActivity.class);
                                intent.putExtra("you",u.getUid());
                                startActivity(intent);
//                               chat chat=new chat(u.getUid());
//                                String url1="/selectChat";
//                                postJwt.sendPostRequest(url, s, new postJwt.OnResultListener() {
//                                    public void onResult(Result result) {
//                                        // 处理返回的Result对象
//                                        if (result != null) {
//                                            if(result.iu!=0){
//
//                                            }}}});
                                return false;
                            }
                        });
                        Toast.makeText(selectUser.this, "查询成功", Toast.LENGTH_SHORT).show();}
                } else {
                    Toast.makeText(selectUser.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}

