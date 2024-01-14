package com.example.androidandweb.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidandweb.O_solidObjects.simpleObjects.Result;
import com.example.androidandweb.O_solidObjects.simpleObjects.privateUser;
import com.example.androidandweb.O_solidObjects.user;
import com.example.androidandweb.Q_sql.mySql;
import com.example.androidandweb.R;
import com.example.androidandweb.http.postNoJwt;

public class signIn extends AppCompatActivity implements View.OnClickListener{

    private mySql MyApp;
    // 获取SharedPreferences对象，参数为文件名和访问模式
    SharedPreferences localState = mySql.getSharedPreferences("LocalState");//本机状态文件
    SharedPreferences.Editor LocalState = localState.edit();

    SharedPreferences localUserMsg = mySql.getSharedPreferences("localUserMsg");//用户数据库
    SharedPreferences.Editor LSM = localUserMsg.edit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        TextView Vid=findViewById(R.id.textVid);//订阅框
        Vid.setOnClickListener(this);
        Button updateVid=findViewById(R.id.updateVid);//更新订阅
        updateVid.setOnClickListener(this);//设置事件
        Button sign=findViewById(R.id.AAWSign);
        sign.setOnClickListener(this);
    }
    //按钮事件判定
    public void onClick(View v){
        if(v.getId()==R.id.AAWSign){
           //调用注册方法
            sign();;
        }
        if(v.getId()==R.id.updateVid){
            TextView Vid=findViewById(R.id.textVid);
            String vid=Vid.getText().toString();
            LocalState.putString("vid", vid);
            LocalState.apply();
            Toast.makeText(signIn.this, "更新成功", Toast.LENGTH_SHORT).show();
        }
        if(v.getId()==R.id.textVid){
            TextView Vid=findViewById(R.id.textVid);
            Vid.setText("");
        }
    }
    public void sign(){
        CheckBox allow=findViewById(R.id.allowAll);
        if(allow.isChecked()){
        TextView nikeName=findViewById(R.id.TextNikeName);
        TextView email=findViewById(R.id.editTextEmailAddress);
        TextView password=findViewById(R.id.editTextPassword);
        String PUEmail=email.getText().toString();
        String PUPassword=password.getText().toString();
        String nikename=nikeName.getText().toString();
        privateUser pu=new privateUser(PUEmail,PUPassword);
        user u=new user(nikename,pu);

        //aaw,传递服务器储存
        String url="/sign";
        postNoJwt.sendPostRequest(url, u, new postNoJwt.OnResultListener() {
            public void onResult(Result result) {
                // 处理返回的Result对象
                if (result != null) {
                    if(result.iu!=0){//注册成功
                        Toast.makeText(signIn.this, "服务器注册成功", Toast.LENGTH_SHORT).show();}
                } else {
                    Toast.makeText(signIn.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //sp,保存到本机
        CheckBox SP=findViewById(R.id.allowSp);
        if(SP.isChecked()){

            //注册到本机
        }
    }else {
            Toast.makeText(signIn.this, "你需要勾选同意app的相关规定", Toast.LENGTH_SHORT).show();
        }
    }
}