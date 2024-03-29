package com.example.androidandweb.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.androidandweb.O_solidObjects.VSmail;
import com.example.androidandweb.R;
import com.example.androidandweb.adapter.VSmailAd;
import com.example.androidandweb.http.ImageLoader;
import com.example.androidandweb.http.PackageHttp;
import com.example.androidandweb.http.postJwt;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class videoDetail extends AppCompatActivity implements View.OnClickListener{

    String url1, VName,intro;
    int vid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        start();
        Button add=findViewById(R.id.addVSmall);
        add.setOnClickListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // 在页面从后台返回前台时触发的操作
        // 例如，显示一个 Toast 消息
//        start();
    }
    @Override
    public void onClick(View v) {
if(v.getId()==R.id.addVSmall){
    addVSmall();
}
    }
    public void addVSmall(){
        Intent intent=new Intent(videoDetail.this,VSmailSubmit.class);
        intent.putExtra("thing","submit");
        intent.putExtra("VName",VName);
        intent.putExtra("VIntro",intro);
        intent.putExtra("img",url1);
        intent.putExtra("vid",vid);
        Log.i("vid", ""+vid);
        startActivity(intent);
        Toast.makeText(videoDetail.this, "上传单项资源", Toast.LENGTH_SHORT).show();
    }
    public void start(){
        Intent intent=getIntent();
        vid=intent.getIntExtra("vid",1);
        url1=new PackageHttp().toImgUrl(intent.getStringExtra("img"));
        ImageView img=findViewById(R.id.VSimg);
        Glide.with(videoDetail.this).load(url1).circleCrop().into(img);
        TextView VSName=findViewById(R.id.VSname);
        VName="作品名称："+intent.getStringExtra("VName");
        VSName.setText(VName);
        TextView VSIntro=findViewById(R.id.VSintro);
        intro="作品简介："+intent.getStringExtra("VIntro");
        VSIntro.setText(intro);
        int vid=intent.getIntExtra("vid",1);
        VSmail vs=new VSmail();
        vs.setVid(vid);
        String url="/getVSmail";
        postJwt.sendPostRequest(url, vs, result -> {
            // 处理返回的Result对象
            if (result != null) {
                if(result.iu!=0){
                    Log.i("测试数据",result.data.toString());
                    String List= new Gson().toJson(result.data);
                    Log.i("测试数据",List);
                    Type type = new TypeToken<java.util.List<VSmail>>(){}.getType();
                    List<VSmail> VSmailList = new Gson().fromJson(List, type);
                    // 现在你可以使用VSmailList了
                    Log.i("测试数据",VSmailList.toString());
                    VSmailAd adapter=new VSmailAd(videoDetail.this, R.layout.s_vsmail,VSmailList);
                    ListView listView=findViewById(R.id.VSmailShow);
                    if (VSmailList.size()==0){
                        Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show();
                    }
                    listView.setAdapter(adapter);
                    listView.setOnItemLongClickListener((parent, view, position, id) -> {

                        VSmail vSmail= VSmailList.get(position);
                        Log.i("点击分项",""+vSmail);
                        if (vSmail.getIns()==1){
                            Intent intent1=new Intent(videoDetail.this, VSmailSubmit.class);
                            intent1.putExtra("thing","change");
                            intent1.putExtra("vid",intent.getIntExtra("vid",1));
                            intent1.putExtra("VName",VName);
                            intent1.putExtra("VIntro",intro);
                            intent1.putExtra("img",url1);
                            intent1.putExtra("sid",vSmail.getSid());
                            intent1.putExtra("name",vSmail.getName());
                            intent1.putExtra("url",vSmail.getUrl());
                            intent1.putExtra("also",vSmail.getAlso());
                            startActivity(intent1);
                        }else {
                            Toast.makeText(videoDetail.this, "上传者禁止修改", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    });
                    listView.setOnItemClickListener((parent, view, position, id) -> {

                        VSmail vSmail= VSmailList.get(position);
                        Log.i("点击分项",""+vSmail);
                        Intent intent12 =new Intent(videoDetail.this, videoShow.class);
                        intent12.putExtra("vUrl",vSmail.getUrl());
                        intent12.putExtra("type",vSmail.getType());
                        startActivity(intent12);
                    });
//                        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                            @Override
//                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                                VSmail vSmail= VSmailList.get(position);
//                                if (vSmail.getIns()==1){
//                                    Intent intent1=new Intent(videoDetail.this, VSmailSubmit.class);
//                                    intent1.putExtra("thing","change");
//                                    intent1.putExtra("vid",intent.getIntExtra("vid",1));
//                                    intent1.putExtra("VName",VName);
//                                    intent1.putExtra("VIntro",intro);
//                                    intent1.putExtra("img",url1);
//                                    intent1.putExtra("sid",vSmail.getSid());
//                                    intent1.putExtra("name",vSmail.getName());
//                                    intent1.putExtra("url",vSmail.getUrl());
//                                    intent1.putExtra("also",vSmail.getAlso());
//                                    startActivity(intent1);
//                                }else {
//                                    Toast.makeText(videoDetail.this, "上传者禁止修改", Toast.LENGTH_SHORT).show();
//                                }
//                                return true;
//                            }
//                        });
//                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                                VSmail vSmail= VSmailList.get(position);
//                                Log.i("点击分项",""+vSmail);
//                                Intent intent=new Intent(videoDetail.this, videoShow.class);
//                                intent.putExtra("vUrl",vSmail.getUrl());
//                                startActivity(intent);
//                            }
//                        });
                }}});

    }


}