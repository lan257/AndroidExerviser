package com.example.androidandweb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidandweb.O_solidObjects.simpleObjects.Result;
import com.example.androidandweb.O_solidObjects.simpleObjects.privateUser;
import com.example.androidandweb.http.PackageURL;
import com.example.androidandweb.http.postNoJwt;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    postNoJwt postNoJwt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button AAWLogin=findViewById(R.id.AAWLogin);
        AAWLogin.setOnClickListener(this);
    }
    public void onClick(View v){
        if(v.getId()==R.id.AAWLogin){
            TextView email=findViewById(R.id.editTextEmailAddress);
            TextView password=findViewById(R.id.editTextPassword);
            String PUEmail=email.getText().toString();
            String PUPassword=password.getText().toString();
            privateUser pu=new privateUser(PUEmail,PUPassword);

            //发送http请求，需要：url,数据，其中数据包含Private{email,password}
            String url = "/login";
            Toast.makeText(MainActivity.this,""+url+pu.toString(),Toast.LENGTH_SHORT).show();
//            String vid="https://cf6d-240e-43d-4108-47f-c0fb-f5f8-fd6-6951.ngrok-free.app";//vid需要改成变量，app前端可以设置。
//            PackageURL packageURL=new PackageURL(vid);
//            url = packageURL.getUrl(url);

            // 调用方法发送POST请求
            postNoJwt.sendPostRequest(url, pu, new postNoJwt.OnResultListener() {
                public void onResult(Result result) {
                    // 处理返回的Result对象
                    if (result != null) {
                        Toast.makeText(MainActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                        // 还可以通过result.getData()获取数据
                    } else {
                        Toast.makeText(MainActivity.this, "Error in HTTP POST request", Toast.LENGTH_SHORT).show();
                    }
                }
            });

}
}}