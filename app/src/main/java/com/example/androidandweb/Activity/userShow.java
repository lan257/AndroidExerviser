package com.example.androidandweb.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidandweb.O_solidObjects.activity;
import com.example.androidandweb.O_solidObjects.lOperator;
import com.example.androidandweb.O_solidObjects.user;
import com.example.androidandweb.Q_sql.mySql;
import com.example.androidandweb.R;
import com.example.androidandweb.adapter.RecyclerItemClickListener;
import com.example.androidandweb.adapter.actAdR;
import com.example.androidandweb.http.ImageLoader;
import com.example.androidandweb.http.PackageHttp;
import com.example.androidandweb.http.postJwt;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class userShow extends AppCompatActivity implements View.OnClickListener{
    user u=new user();
    boolean uIs;//是否为自己主页
    List<activity> activityList;
    TextView love,fan;
    RecyclerView recyclerView;
//    TextView nickname,proMotto,concern,fan,getLove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_show);
        setButtonClick();
        userDataShow();
    }
    @Override
    protected void onResume() {
        super.onResume();
        // 在页面从后台返回前台时触发的操作
        // 例如，显示一个 Toast 消息
    }
    private void setButtonClick() {
        setClick(R.id.concern);
        setClick(R.id.fan);
        setClick(R.id.love);
        setClick(R.id.sent);
        setClick(R.id.actLove);
        setClick(R.id.waitSent);
        recyclerView=findViewById(R.id.userActShow);
    }
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.concern){
            toIntent(selectUser.class,"concern",u.getUid());
        }else if (v.getId()==R.id.fan){
            toIntent(selectUser.class,"fan",u.getUid());
        }else if (v.getId()==R.id.love){
            userConcern();
        }else if (v.getId()==R.id.sent){
            u.setThing(2);
            showSentAct();
            changeColor(R.id.sent);
        }else if (v.getId()==R.id.actLove){
            u.setThing(3);
            showSentAct();
            changeColor(R.id.actLove);
        }else if (v.getId()==R.id.waitSent){
            u.setThing(4);
            showSentAct();
            changeColor(R.id.waitSent);
        }
    }

    private void showSentAct() {
        String url = "/getActList";
        postJwt.sendPostRequest(url, u, result -> {
            // 处理返回的Result对象
            if (result != null) {
                if (result.iu != 0) {
                    Type type = new TypeToken<List<activity>>() {
                    }.getType();
                    activityList = new Gson().fromJson(new Gson().toJson(result.data), type);
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                    actAdR adapter = new actAdR(this, activityList);
                    recyclerView.setAdapter(adapter);
                    if (activityList.size()==0){
                        Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show();
                    }
                    recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            activity activity = activityList.get(position);
                            Toast.makeText(userShow.this, "" + activity.getAid() + position + activityList.get(position).getAid(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(userShow.this, showAct.class);
                            intent.putExtra("aid", activityList.get(position).getAid());
                            startActivity(intent);
                            // 处理点击事件
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {
                            //处理长按事件
                        }
                    }));
                }
            }
        });
    }

    private void changeColor(int v) {
        findViewById(R.id.sent).setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        findViewById(R.id.actLove).setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        findViewById(R.id.waitSent).setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        findViewById(v).setBackgroundColor(ContextCompat.getColor(this, R.color.s111));
    }

    @SuppressLint("SetTextI18n")
    private void userConcern() {
        if (!uIs){
        lOperator lp=new lOperator(u.getUid(),1,u.isLoveIs()?2:1,0);
        String url = "/Love+-";
        postJwt.sendPostRequest(url, lp, result1 -> {
            // 处理返回的Result对象
            if (result1 != null) {
                if (result1.iu != 0) {
                    if (u.isLoveIs()) {
                        u.setLoveIs(false);
                        u.setFan(u.getFan()-1);
                        fan.setText("" + u.getFan());
                        love.setText(u.isLoveIs()?"已关注":"+关注");
                        Toast.makeText(this, "取消关注", Toast.LENGTH_SHORT).show();
                    } else {
                        u.setLoveIs(true);
                        u.setFan(u.getFan()+1);
                        fan.setText("" + u.getFan());
                        love.setText(u.isLoveIs()?"已关注":"+关注");
                        Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show();
                    }
                }}});}
        else {
            toIntent(index.class,"编辑个人信息",u.getUid());
        }
    }

    private void toIntent(Class<?> Class, String thing, int id) {
        Intent intent=new Intent(this,Class);
        intent.putExtra("thing",thing);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    private void showUserAct() {
        u.setThing(2);
        showSentAct();
        changeColor(R.id.sent);
    }
//查明来意
    private void userDataShow() {
        Intent intent=getIntent();
        String thing=intent.getStringExtra("thing");
        u.setUid(intent.getIntExtra("id",0));
        getUser();
        if (Objects.equals(thing, "change")){
            //触发修改页面
        }
    }

    @SuppressLint("SetTextI18n")
    public void getUser() {
        String url = "/uid/selectUser";
        postJwt.sendPostRequest(url, u, result -> {
            // 处理返回的Result对象
            if (result != null) {
                if (result.iu == 1) {
                    u = new Gson().fromJson(new Gson().toJson(result.data), user.class);
                    ImageView userImg = findViewById(R.id.userImg);
                    String imageUrl = new PackageHttp().toImgUrl(u.getImg());
                    Glide.with(userShow.this).load(imageUrl).circleCrop().into(userImg);
                    TextView nickname = findViewById(R.id.nickname), proMotto = findViewById(R.id.proMotto), concern = findViewById(R.id.concernNum), getLove = findViewById(R.id.getLoveNum);
                    love=findViewById(R.id.love);
                    fan = findViewById(R.id.fanNum);
                    nickname.setText(u.getNickname());
                    proMotto.setText(u.getProMotto());
                    concern.setText("" + u.getConcern());
                    fan.setText("" + u.getFan());
                    getLove.setText("" + u.getLove());
                    uIs= u.getUid() == mySql.getSharedPreferences("LocalState").getInt("uid", 1);
                    love.setText(uIs?"编辑":u.isLoveIs()?"已关注":"+关注");
                    //展示发布活动
                    showUserAct();
                }
            }
        });
    }


    private void setClick(int viewId) {
        View view=findViewById(viewId);
        view.setOnClickListener(this);
    }
}