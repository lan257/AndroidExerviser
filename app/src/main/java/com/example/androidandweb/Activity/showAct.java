package com.example.androidandweb.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidandweb.O_solidObjects.simpleObjects.aCon;
import com.example.androidandweb.O_solidObjects.user;
import com.example.androidandweb.R;
import com.example.androidandweb.adapter.aConAd;
import com.example.androidandweb.adapter.actAdR;
import com.example.androidandweb.http.PackageHttp;
import com.example.androidandweb.http.postJwt;
import com.example.androidandweb.O_solidObjects.activity;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class showAct extends AppCompatActivity implements View.OnClickListener{
    activity act=new activity();
    String url;
    int aid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        setButtonClick();
        actShow();
        myTest();
    }

    private void myTest() {
        String url = "/getActList";
        byte s = 0;
        postJwt.sendPostRequest(url, s, result -> {
            // 处理返回的Result对象
            if (result != null) {
                if (result.iu!=0) {
                    Type type = new TypeToken<List<activity>>(){}.getType();
                    List<activity> activityList = new Gson().fromJson(new Gson().toJson(result.data), type);
                    RecyclerView recyclerView=findViewById(R.id.comShow);
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                    actAdR adapter = new actAdR(this, activityList);
                    recyclerView.setAdapter(adapter);}}});
    }

    private void setButtonClick() {
        Intent intent=getIntent();
        Log.i("activityAid",""+intent.getIntExtra("aid",1));
        aid=intent.getIntExtra("aid",1);
    }

    @Override
    public void onClick(View v) {

    }
    @SuppressLint("SetTextI18n")
    private void actShow() {
        url="/actSelect" ;
        act.setAid(aid);
        postJwt.sendPostRequest(url,act, result -> {
            // 处理返回的Result对象
            if (result != null) {
                if (result.iu!=0) {
                    act= new Gson().fromJson(new Gson().toJson(result.data), activity.class);
                    showSimple();//静态资源展示
                    showActList();//活动内容展示
                }}});
    }

    private void showActList() {
        List<aCon> aConList= act.GContent();
//        Log.i("初始化失败",""+act);
        aConAd adapter=new aConAd(showAct.this, R.layout.s_acon,aConList,-1);
        ListView listView=findViewById(R.id.actShow);
        listView.setAdapter(adapter);
        setHigh();
    }

    private void showSimple() {
        //头像展示
        ImageView imageView=findViewById(R.id.youImger);Glide.with(this).load(new PackageHttp().toImgUrl(act.getU().getImg())).circleCrop().into(imageView);
        //昵称展示
        TextView nickname= findViewById(R.id.user_nickname);nickname.setText(act.getU().getNickname());
        TextView titleText= findViewById(R.id.titleText);titleText.setText(act.getTitleText());
        TextView time= findViewById(R.id.createTime);time.setText("发布于："+new PackageHttp().formatTime2(act.getCreateTime()) +"  编辑于:"+new PackageHttp().formatTime(act.getChangeTime()));
    }
    //限高
    private void setHigh(){
        // 获取父布局的总高度
        int parentHeight = findViewById(R.id.father).getHeight();
        // 获取控件一的高度
        int view1Height = findViewById(R.id.view1).getHeight();
        // 获取控件三的高度
        int view3Height = findViewById(R.id.view3).getHeight();
// 计算控件二的最大高度
        int maxView2Height = parentHeight - view1Height - view3Height;
// 设置控件二的最大高度
        final LinearLayout linearLayout = findViewById(R.id.xHigh);
        ViewTreeObserver vto = linearLayout.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int actualView2Height = linearLayout.getHeight();
                // 设置控件二的最大高度，只有在实际高度小于计算出的最大高度时设置
                if (actualView2Height > maxView2Height) {
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                    layoutParams2.height = maxView2Height;
                    linearLayout.setLayoutParams(layoutParams2);
                }
                // 移除监听器，确保只执行一次
                linearLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
}}