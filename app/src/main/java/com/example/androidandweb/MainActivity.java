package com.example.androidandweb;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidandweb.O_solidObjects.simpleObjects.Result;
import com.example.androidandweb.O_solidObjects.simpleObjects.privateUser;
import com.example.androidandweb.http.postNoJwt;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // 获取SharedPreferences对象，参数为文件名和访问模式
    SharedPreferences sharedPreferences = MyApp.getSharedPreferences();
    SharedPreferences.Editor editor = sharedPreferences.edit();
    postNoJwt postNoJwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button AAWLogin=findViewById(R.id.AAWLogin);//主动登录
        AAWLogin.setOnClickListener(this);//设置事件
        Button updateVid=findViewById(R.id.updateVid);//更新订阅
        updateVid.setOnClickListener(this);//设置事件
        TextView Vid=findViewById(R.id.textVid);
        Vid.setOnClickListener(this);
        loginAuto();
    }
//按钮事件判定
    public void onClick(View v){
        if(v.getId()==R.id.AAWLogin){
            login();//调用登录方法
        }
        if(v.getId()==R.id.updateVid){
            TextView Vid=findViewById(R.id.textVid);
            String vid=Vid.getText().toString();
            editor.putString("vid", vid);
            editor.apply();
            Toast.makeText(MainActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
        }
        if(v.getId()==R.id.textVid){
            TextView Vid=findViewById(R.id.textVid);
        Vid.setText("");
        }
    }


    //登录方法
    void login(){
        CheckBox allow=findViewById(R.id.allowAll);
        if(allow.isChecked()){
        TextView email=findViewById(R.id.editTextEmailAddress);
        TextView password=findViewById(R.id.editTextPassword);
        String PUEmail=email.getText().toString();
        String PUPassword=password.getText().toString();
        privateUser pu=new privateUser(PUEmail,PUPassword);
        //发送http请求，需要：url,数据，其中数据包含Private{email,password}
        String url = "/login";

        // 调用方法发送POST请求
        postNoJwt.sendPostRequest(url, pu, new postNoJwt.OnResultListener() {
            public void onResult(Result result) {
                // 处理返回的Result对象
                if (result != null) {

                    //数据处理
//                    Toast.makeText(MainActivity.this, result.msg, Toast.LENGTH_SHORT).show();
                    if(result.iu!=0){//登陆成功
                    //将jwt存入键值对存储
                    editor.putString("token", result.data.toString());
                    editor.apply();
//                    String jwt=sharedPreferences.getString("token", "none");//读取token
                    Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_LONG).show();

                    CheckBox remember=findViewById(R.id.ARemember);//检测记住账号
                    CheckBox auto=findViewById(R.id.AAuto);//检测自动登录

                    //存储登录信息
                        if(remember.isChecked()){
                            editor.putString("remember","true");
                            editor.putString("email",PUEmail);
                            editor.putString("password",PUPassword);
                            editor.apply();
                            if (auto.isChecked()){
                                editor.putString("auto","true");
                                editor.apply();
                            }else {editor.putString("auto","false");
                            editor.apply();}
                        }else {
                            editor.putString("remember","false");
                            editor.apply();
                        }

                        //跳转主页，待做
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
//                Thread.sleep(5000);
                    // 在触发登录前等待5秒
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(auto.isChecked()){
                                login();
                            }
                        }
                    }, 5000);
                }
            }
        }}
    }
}