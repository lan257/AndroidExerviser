package com.example.androidandweb.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.androidandweb.O_solidObjects.VSmail;
import com.example.androidandweb.O_solidObjects.simpleObjects.Result;
import com.example.androidandweb.R;
import com.example.androidandweb.http.PackageHttp;
import com.example.androidandweb.http.postJwt;

import java.util.Objects;

public class VSmailSubmit extends AppCompatActivity implements View.OnClickListener{
    Intent intent;
    int sid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vsmail_submit);
        intent=getIntent();
        Log.i("查询参数",intent.getStringExtra("img")+intent.getStringExtra("VName")+intent.getStringExtra("VIntro"));
        start(intent);
        Button VSSubmit=findViewById(R.id.AAWVSSubmit);
        VSSubmit.setOnClickListener(this);
        Button update=findViewById(R.id.AAWVSUpdate);
        update.setOnClickListener(this);
        String thing=intent.getStringExtra("thing");
        if(Objects.equals(thing, "change")){
            VSSubmit.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
            TextView title=findViewById(R.id.title);
            title.setText("修正单项资源");
            sid=intent.getIntExtra("vid",1);
            startChange();
        }
    }

    private void startChange() {
        EditText name=findViewById(R.id.VSName);
        TextView url=findViewById(R.id.VSUrl);
        EditText also=findViewById(R.id.also);
        Log.i("为什么没有得到",intent.getStringExtra("name")+intent.getStringExtra("also"));
        name.setText(intent.getStringExtra("name"));
        url.setText(intent.getStringExtra("url"));
        also.setText(intent.getStringExtra("also"));
    }

    public void start(Intent intent){

        ImageView img=findViewById(R.id.VSimg);
        Glide.with(VSmailSubmit.this).load(intent.getStringExtra("img")).circleCrop().into(img);
        TextView VSName=findViewById(R.id.VSname);
        VSName.setText(intent.getStringExtra("VName"));
        TextView VSIntro=findViewById(R.id.VSintro);
        VSIntro.setText(intent.getStringExtra("VIntro"));

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.AAWVSSubmit){
            Log.i("触发点击事件","点击");
            AAWVSSubmit("/AAWVSSubmit");
        }
        if (v.getId()==R.id.AAWVSUpdate){
            Log.i("触发点击事件","点击更新");
            AAWVSSubmit("/AAWVSUpdate");
        }
    }
    public void AAWVSSubmit(String url){
        VSmail vs=new VSmail();
        EditText VSName=findViewById(R.id.VSName);
        vs.setName(VSName.getText().toString());
        EditText VSUrl=findViewById(R.id.VSUrl);
        if(!VSUrl.getText().toString().equals("")){
            vs.setUrl(VSUrl.getText().toString());}
        EditText also=findViewById(R.id.also);
        if(!VSUrl.getText().toString().equals("")){
            vs.setAlso(also.getText().toString());}
        CheckBox allow=findViewById(R.id.allowChange);
        if(allow.isChecked()){vs.setIns(1);}else {vs.setIns(0);}
        CheckBox uid=findViewById(R.id.showName);
        if(uid.isChecked()){vs.setUid(1);}
        vs.setVid(intent.getIntExtra("vid",1));
        if (sid!=0){vs.setSid(sid);}
        Log.i("上传数据",""+vs);
        postJwt.sendPostRequest(url, vs, new postJwt.OnResultListener() {
            public void onResult(Result result) {
                // 处理返回的Result对象
                if (result != null) {
                    if(result.iu!=0){//上传成功
                        Toast.makeText(VSmailSubmit.this, "服务器上传成功", Toast.LENGTH_SHORT).show();}
                    else {
                        Toast.makeText(VSmailSubmit.this, result.msg, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(VSmailSubmit.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
