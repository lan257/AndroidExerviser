package com.example.androidandweb.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    @Override
    protected void onResume() {
        super.onResume();
        // 在页面从后台返回前台时触发的操作
        // 例如，显示一个 Toast 消息

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
        postJwt.sendPostRequest(url, s, result -> {
            // 处理返回的Result对象
            if (result != null) {
                if(result.iu!=0){
                    String List= new Gson().toJson(result.data);
                    Type type = new TypeToken<List<user>>(){}.getType();
                    List<user> userList = new Gson().fromJson(List, type);
                    // 现在你可以使用userList了
                    // ...


                    userAd adapter=new userAd(selectUser.this, R.layout.s_layout_user_show,userList);
                    ListView listView=findViewById(R.id.userShow);
                    listView.setAdapter(adapter);
                    if (userList.size()==0){
                        Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show();
                    }
                    listView.setOnItemLongClickListener((parent, view, position, id) -> {

                        user u= userList.get(position);
                        //跳转chatActivity页面
                        Intent intent=new Intent(selectUser.this, chatActivity.class);
                        intent.putExtra("you",u.getUid());
                        startActivity(intent);
                        return false;
                    });
                    Toast.makeText(selectUser.this, "查询成功", Toast.LENGTH_SHORT).show();}
            } else {
                Toast.makeText(selectUser.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

}

