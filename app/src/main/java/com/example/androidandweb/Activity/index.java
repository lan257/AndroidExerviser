package com.example.androidandweb.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidandweb.O_solidObjects.simpleObjects.Result;
import com.example.androidandweb.O_solidObjects.user;
import com.example.androidandweb.O_solidObjects.video;
import com.example.androidandweb.R;
import com.example.androidandweb.adapter.userAd;
import com.example.androidandweb.adapter.videoAd;
import com.example.androidandweb.http.PackageHttp;
import com.example.androidandweb.http.postJwt;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.navigation.NavigationView;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

import lombok.NonNull;

public class index extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout index;

    user me;
    PackageHttp packageHttp=new PackageHttp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        index=findViewById(R.id.index);
        getUser();
        ImageView mi=findViewById(R.id.userImg);
        mi.setOnClickListener(this);
        NavigationView navigationView = findViewById(R.id.left);
        navigationView.setNavigationItemSelectedListener(item -> {
            selectMenu(item.getItemId());// 在这里处理菜单项的点击事件
            return true;
        });
        Button SV=findViewById(R.id.selectVideo);
        SV.setOnClickListener(this);
//        ListView.setOnItemClickListener(this);

    }

//        SimpleExoPlayer player = new SimpleExoPlayer.Builder(this).build();
//        PlayerView playerView = findViewById(R.id.playerView);
//
//// 将Player关联到PlayerView
//        playerView.setPlayer(player);
//
//// 准备媒体资源
//        MediaItem mediaItem = MediaItem.fromUri("https://sns-video-bd.xhscdn.com/spectrum/06f77ba84733159ec47e3c525fd7ca0f0eabdfb4");
//        player.setMediaItem(mediaItem);
//
//// 准备播放器
//        player.prepare();
//
//// 开始播放
//      //  player.play();


    public void onClick(View v) {
        if(v.getId()==R.id.userImg){

            index.openDrawer(GravityCompat.START);
            String imageUrl = packageHttp.toImgUrl(me.getImg());
            ImageView imageView=findViewById(R.id.userImger);
            // 使用 Glide 或其他图片加载库加载在线图片
            Glide.with(this).load(imageUrl).into(imageView);
            TextView nickname=findViewById(R.id.user_nickname);
            nickname.setText(me.getNickname());
        }
        if(v.getId()==R.id.selectVideo){
            AAWS();
        }
    }
    //处理侧边框选项管理
    public void selectMenu(int itemId) {
        if(itemId==R.id.select1){
            Log.i("","我点击了选项一");
            Toast.makeText(index.this, "选项一", Toast.LENGTH_SHORT).show();
        }

        if(itemId==R.id.select2){Log.i("","我点击了选项二");
            Toast.makeText(index.this, "选项二", Toast.LENGTH_SHORT).show();
        }if(itemId==R.id.select3){
            Toast.makeText(index.this, "选项三", Toast.LENGTH_SHORT).show();
        }if(itemId==R.id.select4){
            Toast.makeText(index.this, "选项四", Toast.LENGTH_SHORT).show();
        }
        if(itemId==R.id.selectUser){
            Intent intent=new Intent(index.this, selectUser.class);
            startActivity(intent);
            Toast.makeText(index.this, "查询用户", Toast.LENGTH_SHORT).show();
        }if(itemId==R.id.selectVideoSubmit){
            Intent intent=new Intent(index.this,videoSubmit.class);
            intent.putExtra("thing","submit");
            startActivity(intent);
            Toast.makeText(index.this, "上传视频资源", Toast.LENGTH_SHORT).show();
        }
    }
    public void AAWS(){
        EditText name=findViewById(R.id.videoName);
        String Name;
        if(name.getText().toString().equals("")){
            Name="all";
        }else {
            Name = name.getText().toString();}
        video v=new video();
        v.setVName(Name);
        String url="/selectVideo";
        Log.i("触发查询事件",v.toString());
        postJwt.sendPostRequest(url, v, new postJwt.OnResultListener() {
            public void onResult(Result result) {
                // 处理返回的Result对象
                if (result != null) {
                    if(result.iu!=0){
                        Log.i("测试数据",result.data.toString());
                        String List= new Gson().toJson(result.data);
                        Log.i("测试数据",List);
                        Type type = new TypeToken<List<video>>(){}.getType();
                        List<video> videoList = new Gson().fromJson(List, type);
                        // 现在你可以使用userList了
                        // ...
                        Log.i("测试数据",videoList.toString());
                        videoAd adapter=new videoAd(index.this, R.layout.video,videoList);
                        ListView listView=findViewById(R.id.videoShow);
                        listView.setAdapter(adapter);
                        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                video video= videoList.get(position);
                                if (video.getIns()==1){
                                    Intent intent=new Intent(index.this, videoSubmit.class);
                                    intent.putExtra("vid",video.getVid());
                                    intent.putExtra("thing","change");
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(index.this, "上传者禁止修改", Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            }
                        });
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                video video= videoList.get(position);
                                    Intent intent=new Intent(index.this, videoShow.class);
                                    intent.putExtra("vUrl",video.getVUrl());
                                    startActivity(intent);
                            }
                        });
                        Toast.makeText(index.this, "查询成功", Toast.LENGTH_SHORT).show();}
                } else {
                    Toast.makeText(index.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    //获取个人基本信息
    public void getUser() {
        String url = "/getUserJwt";
        byte s = 0;
        postJwt.sendPostRequest(url, s, new postJwt.OnResultListener() {
            public void onResult(Result result) {
                // 处理返回的Result对象
                if (result != null) {
                    if(result.iu!=0){

                        Log.i("个人信息1",result.iu+result.msg+result.data);
                        String user= new Gson().toJson(result.data);
//                        Type type = new TypeToken<user>(){}.getType();
                        me= new Gson().fromJson(user, user.class);
                        Log.i("个人信息2",me.toString());
                        Log.i("个人信息",me.toString());
                        ImageView mi=findViewById(R.id.userImg);
                        String imageUrl = packageHttp.toImgUrl(me.getImg());
                        Glide.with(index).load(imageUrl).into(mi);
                    }}else {Log.i("似乎没有信息","似乎没有得到消息");}
            }
        });
    }
}