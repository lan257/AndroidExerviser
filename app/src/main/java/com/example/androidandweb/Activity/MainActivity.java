package com.example.androidandweb.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidandweb.O_solidObjects.simpleObjects.Result;
import com.example.androidandweb.O_solidObjects.user;
import com.example.androidandweb.Q_sql.mySql;
import com.example.androidandweb.R;
import com.example.androidandweb.http.postNoJwt;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // 获取SharedPreferences对象，参数为文件名和访问模式
    SharedPreferences sharedPreferences = mySql.getSharedPreferences("LocalState");//本机状态文件
    SharedPreferences.Editor LocalState = sharedPreferences.edit();


    postNoJwt postNoJwt;
    int isLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isLogin=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button AAWLogin=findViewById(R.id.AAWLogin);//主动登录
        AAWLogin.setOnClickListener(this);//设置事件
        Button updateVid=findViewById(R.id.updateVid);//更新订阅
        updateVid.setOnClickListener(this);//设置事件
        TextView Vid=findViewById(R.id.textVid);//订阅框
        Vid.setOnClickListener(this);
        TextView signIn=findViewById(R.id.TextSignIn);
        signIn.setOnClickListener(this);
        TextView test=findViewById(R.id.TextForget);
        //test.setOnClickListener(this);
        loginAuto();
    }
//按钮事件判定
    public void onClick(View v){
        if(v.getId()==R.id.AAWLogin){
            isLogin=0;
            login();//调用登录方法
        }
        if(v.getId()==R.id.updateVid){
            TextView Vid=findViewById(R.id.textVid);
            String vid=Vid.getText().toString();
            LocalState.putString("vid", vid);
            LocalState.apply();
            Toast.makeText(MainActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
        }
        if(v.getId()==R.id.textVid){
            TextView Vid=findViewById(R.id.textVid);
            Vid.setText("");
        }
        if (v.getId() == R.id.TextSignIn){
            isLogin=1;
            Intent sign=new Intent(MainActivity.this, signIn.class);
            startActivity(sign);
        }
        if (v.getId() == R.id.TextForget){
            isLogin=1;
            Intent test=new Intent(MainActivity.this, sqlTest.class);
            startActivity(test);
        }
    }


    //登录方法
    void login(){
        CheckBox allow=findViewById(R.id.allowAll);
        if(allow.isChecked()){
            if(isLogin==1){
                return;
            }
        TextView email=findViewById(R.id.editTextEmailAddress);
        TextView password=findViewById(R.id.editTextPassword);
        String PUEmail=email.getText().toString();
        String PUPassword=password.getText().toString();
        user pu=new user(PUEmail,PUPassword);
        //发送http请求，需要：url,数据，其中数据包含Private{email,password}
        String url = "/login";

        Log.i("触发",pu.toString());
        // 调用方法发送POST请求
        postNoJwt.sendPostRequest(url, pu, new postNoJwt.OnResultListener() {
            public void onResult(Result result) {
                // 处理返回的Result对象
                if (result != null) {

                    //数据处理
//                    Toast.makeText(MainActivity.this, result.msg, Toast.LENGTH_SHORT).show();
                    if(result.iu!=0){//登陆成功
                    //将jwt存入键值对存储
                    LocalState.putString("token", result.data.toString());
                        LocalState.apply();
//                    String jwt=sharedPreferences.getString("token", "none");//读取token
                    Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

                    CheckBox remember=findViewById(R.id.ARemember);//检测记住账号
                    CheckBox auto=findViewById(R.id.AAuto);//检测自动登录

                    //存储登录信息
                        if(remember.isChecked()){
                            LocalState.putString("remember","true");
                            LocalState.putString("email",PUEmail);
                            LocalState.putString("password",PUPassword);
                            LocalState.apply();
                            if (auto.isChecked()){
                                LocalState.putString("auto","true");
                                LocalState.apply();
                            }else {LocalState.putString("auto","false");
                                LocalState.apply();}
                        }else {
                            LocalState.putString("remember","false");
                            LocalState.apply();
                        }
                        isLogin=1;
                        Intent intent=new Intent(MainActivity.this, index.class);
                        startActivity(intent);
                        //跳转主页
                    }

                    // 还可以通过result.getData()获取数据
                } else {
                    Toast.makeText(MainActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                }
            }
        });}else {
            Toast.makeText(MainActivity.this, "你需要勾选同意app的相关规定", Toast.LENGTH_SHORT).show();
        }
    }

    //读取登录信息
    void loginAuto() {
        CheckBox remember=findViewById(R.id.ARemember);//检测记住账号
        CheckBox auto=findViewById(R.id.AAuto);//检测自动登录
        TextView email=findViewById(R.id.editTextEmailAddress);
        TextView password=findViewById(R.id.editTextPassword);
        if(!sharedPreferences.getString("remember", "").equals("")){
        String REM=sharedPreferences.getString("remember", "");
        if(REM.equals("true")){
            email.setText(sharedPreferences.getString("email", ""));
            password.setText(sharedPreferences.getString("password", ""));
            remember.setChecked(true);
            if (!sharedPreferences.getString("auto", "").equals("")){
            String AUTO=sharedPreferences.getString("auto", "");
            if(AUTO.equals("true")){
                auto.setChecked(true);

                    // 在触发登录前等待5秒
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(auto.isChecked()){
                                login();
                            }
                        }
                    }, 3000);
                }
            }
        }}
    }
}