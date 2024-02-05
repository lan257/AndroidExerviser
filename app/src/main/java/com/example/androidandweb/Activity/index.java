package com.example.androidandweb.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidandweb.O_solidObjects.activity;
import com.example.androidandweb.O_solidObjects.simpleObjects.chatList;
import com.example.androidandweb.O_solidObjects.user;
import com.example.androidandweb.O_solidObjects.video;
import com.example.androidandweb.Q_sql.mySql;
import com.example.androidandweb.R;
import com.example.androidandweb.adapter.actAdR;
import com.example.androidandweb.adapter.chatListAd;
import com.example.androidandweb.adapter.userAd;
import com.example.androidandweb.adapter.videoAd;
import com.example.androidandweb.http.PackageHttp;
import com.example.androidandweb.http.postJwt;
import com.example.androidandweb.http.getJwt;
import com.google.android.material.navigation.NavigationView;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.List;
import com.example.androidandweb.adapter.RecyclerItemClickListener;

public class index extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout index;
    user me;
    int s=1;
    TextView SActivity,SVideo,SUser,hello;
    List<activity> activityList;
    ListView listView;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        index=findViewById(R.id.index);
        setButtonClick();
        hello();
        getUser();
    }

    private void setButtonClick() {
        NavigationView navigationView = findViewById(R.id.left);
        navigationView.setNavigationItemSelectedListener(item -> {
            selectMenu(item.getItemId());// 在这里处理菜单项的点击事件
            return true;});
        ImageButton SV=findViewById(R.id.select);
        SV.setOnClickListener(this);
        ImageView mi=findViewById(R.id.userImg);
        mi.setOnClickListener(this);
        SActivity=findViewById(R.id.activity);
        SActivity.setOnClickListener(this);
        SVideo=findViewById(R.id.video);
        SVideo.setOnClickListener(this);
        SUser=findViewById(R.id.user);
        SUser.setOnClickListener(this);
        hello=findViewById(R.id.hello);
        hello.setOnClickListener(this);
        listView=findViewById(R.id.ListShow);
        recyclerView=findViewById(R.id.recyclerView);
    }
    public void onClick(View v) {
        if(v.getId()==R.id.userImg){
            openLift();//打开侧边栏
        }
        if(v.getId()==R.id.select){
            AAWS();//查询资源
        }

        SIndex(v);
    }
    //展示页面
    public void AAWS(){
        EditText name=findViewById(R.id.ResName);
        String Name;
        if(name.getText().toString().equals("")){
            Name="all";
        }else {
            Name = name.getText().toString();}
        if (s==3) {
            video v = new video();
            v.setVName(Name);
            String url = "/selectVideo";
            Log.i("触发查询事件", v.toString());
            postJwt.sendPostRequest(url, v, result -> {
                // 处理返回的Result对象
                if (result != null) {
                    if (result.iu != 0) {
                        Log.i("测试数据", result.data.toString());
                        String List = new Gson().toJson(result.data);
                        Log.i("测试数据", List);
                        Type type = new TypeToken<List<video>>() {
                        }.getType();
                        List<video> videoList = new Gson().fromJson(List, type);
                        hello.setVisibility(View.GONE);
                        // 现在你可以使用userList了
                        // ...
                        Log.i("测试数据", videoList.toString());
                        if (videoList.size() == 0) {
                            Toast.makeText(index.this, "尚未收纳资源", Toast.LENGTH_SHORT).show();
                        }
                        videoAd adapter = new videoAd(index.this, R.layout.s_video, videoList);
                        listView.setAdapter(adapter);
                        showS(listView,SVideo);
                        listView.setOnItemLongClickListener((parent, view, position, id) -> {
                            video video = videoList.get(position);
                            if (video.getIns() == 1) {
                                Intent intent = new Intent(index.this, videoSubmit.class);
                                intent.putExtra("vid", video.getVid());
                                intent.putExtra("thing", "change");
                                startActivity(intent);
                            } else {
                                Toast.makeText(index.this, "上传者禁止修改", Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        });
                        listView.setOnItemClickListener((parent, view, position, id) -> {

                            video video = videoList.get(position);
                            Intent intent = new Intent(index.this, videoDetail.class);
                            intent.putExtra("vid", video.getVid());
                            intent.putExtra("VName", video.getVName());
                            intent.putExtra("VIntro", video.getVIntro());
                            Log.i("测试", "" + video.getVid());
                            intent.putExtra("img", video.getImg());
                            startActivity(intent);
                        });
                        Toast.makeText(index.this, "查询成功", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(index.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                }
            });
        }//查询视频资源
        else if(s==2){
            user s=new user();
            s.setNickname(Name);
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
                        userAd adapter=new userAd(index.this, R.layout.s_layout_user_show,userList);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener((parent, view, position, id) -> {
                            user u= userList.get(position);
                            //跳转chatActivity页面
                            Intent intent=new Intent(index.this, chatActivity.class);
                            intent.putExtra("you",u.getUid());
                            startActivity(intent);

                        });
                        Toast.makeText(index.this, "查询成功", Toast.LENGTH_SHORT).show();}
                } else {
                    Toast.makeText(index.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                }
            });}
    }
    private void showVideo() {
        hello();
    }
    //展示私信列表
    private void showUser() {
        String url = "/getChatList";
        byte s = 0;
        postJwt.sendPostRequest(url, s, result -> {
            // 处理返回的Result对象
            if (result != null) {
                Type type = new TypeToken<List<chatList>>(){}.getType();
                List<chatList> chatList =new Gson().fromJson(new Gson().toJson(result.data), type);
                chatListAd adapter=new chatListAd(this, R.layout.s_chat_list_show,chatList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener((parent, view, position, id) -> {
                    chatList cl= chatList.get(position);
                    //跳转chatActivity页面
                    Intent intent=new Intent(this, chatActivity.class);
                    intent.putExtra("you",cl.getUser().getUid());
                    startActivity(intent);
                });}});}

    private void showAct() {
        String url = "/getActList";
        getJwt.sendGetRequest(url, result -> {
                    // 处理返回的Result对象
            if (result != null) {
                if (result.iu!=0) {
                    Type type = new TypeToken<List<activity>>(){}.getType();
                    activityList = new Gson().fromJson(new Gson().toJson(result.data), type);
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                    actAdR adapter = new actAdR(this, activityList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            activity activity = activityList.get(position);
                            Toast.makeText(index.this, ""+activity.getAid()+position+activityList.get(position).getAid(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(index.this, showAct.class);
                            intent.putExtra("aid",activityList.get(position).getAid());
//                            Log.i("activityAid",""+activity);
                            startActivity(intent);
                            // 处理点击事件
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {
                            // 处理长按事件
                        }
                    }));
                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            // 处理滑动事件，dx和dy分别是水平和垂直方向上的滑动距离
                            // 可以根据需要进行相应的操作
                            if (dy>0){
                                //处理上划事件
                            }if (dy<0){
                                //处理下划事件
                            }if (dx>0){
                                //处理右划事件
                            }if (dx<0){
                                //处理左划事件
                            }}});}}});
    }
    //选择顶部操作
    private void SIndex(View v) {
        if (v.getId()==R.id.activity||(v.getId()==R.id.hello&&s==1)){
            s=1;
            hello.setText("加载中...");
            showS(hello,SUser);
            showAct();
            showS(recyclerView,SActivity);
        }if (v.getId()==R.id.user){
            s=2;
            hello.setText("加载中...");
            showS(hello,SUser);
            showUser();
            showS(listView,SUser);
        }if (v.getId()==R.id.video){
            s=3;
            hello.setText("加载中...");
            showS(hello,SVideo);
            showVideo();
            showS(hello,SVideo);
        }
    }
    private void showS(View view1,View view2){
    hello.setVisibility(View.GONE);
    listView.setVisibility(View.GONE);
    recyclerView.setVisibility(View.GONE);
    SActivity.setBackgroundColor(ContextCompat.getColor(this, R.color.s111));
    SVideo.setBackgroundColor(ContextCompat.getColor(this, R.color.s111));
    SUser.setBackgroundColor(ContextCompat.getColor(this, R.color.s111));
    view1.setVisibility(View.VISIBLE);
    view2.setBackgroundColor(ContextCompat.getColor(this, R.color.s113));
}
    //处理侧边框选项管理
    public void selectMenu(int itemId) {
        if (itemId==R.id.userShow){
            Intent intent=new Intent(index.this,userShow.class);
            intent.putExtra("thing","show");
            intent.putExtra("uid",me.getUid());
            startActivity(intent);
            Toast.makeText(this, "打开主页", Toast.LENGTH_SHORT).show();
        }if(itemId==R.id.VideoSubmit){
            Intent intent=new Intent(index.this,videoSubmit.class);
            intent.putExtra("thing","submit");
            startActivity(intent);
            Toast.makeText(index.this, "上传视频资源", Toast.LENGTH_SHORT).show();
        }if (itemId==R.id.logout){
            logout();
            toLogin();
        }
        if (itemId==R.id.cont){
            Intent intent=new Intent(index.this, SASActivity.class);
            intent.putExtra("thing","submit");
            startActivity(intent);
            Toast.makeText(index.this, "发布活动", Toast.LENGTH_SHORT).show();
        }
    }
    //打开侧边框
    private void openLift() {
        index.openDrawer(GravityCompat.START);
        try {
            String imageUrl =new PackageHttp().toImgUrl(me.getImg());
            ImageView imageView=findViewById(R.id.userImager);
            // 使用 Glide 或其他图片加载库加载在线图片
            Glide.with(this).load(imageUrl).circleCrop().into(imageView);
            TextView nickname=findViewById(R.id.user_nickname);
            nickname.setText(me.getNickname());
        } catch (NullPointerException e){
            Toast.makeText(this, "啊", Toast.LENGTH_SHORT).show();
            getUser();
        }
    }
    //登出账号
    public void logout(){
        SharedPreferences sharedPreferences = mySql.getSharedPreferences("LocalState");//本机状态文件
        SharedPreferences.Editor LocalState = sharedPreferences.edit();
        LocalState.putString("token", "");
        LocalState.apply();
    }
    //获取个人基本信息
    public user getUser() {
        String url = "/getUserJwt";
        byte s = 0;

            postJwt.sendPostRequest(url, s, result -> {
                // 处理返回的Result对象
                if (result != null) {
                    Log.i("个人信息1",result.iu+result.msg+result.data);
                    if(result.iu==1){


//                        String user= new Gson().toJson(result.data);
                        me= new Gson().fromJson(new Gson().toJson(result.data), user.class);
                        ImageView mi=findViewById(R.id.userImg);
                        String imageUrl = new PackageHttp().toImgUrl(me.getImg());
                        Glide.with(index.this).load(imageUrl).circleCrop().into(mi);
                        SharedPreferences.Editor LocalState = mySql.getSharedPreferences("LocalState").edit();//本机状态文件
                        LocalState.putInt("uid", me.getUid());
                        LocalState.apply();
                    }
//                        else if ((int)result.data==401){toLogin();}
                    else if(result.iu==0){
                        toLogin();
                    }}});

        return me;
    }
    public void toLogin(){
        Toast.makeText(this, "未登录，请登录", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void hello() {
        Log.i("时间","你好，几点了？");
        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();
        Log.i("时间","你好，"+currentTime+"点了");
        hello.setVisibility(View.VISIBLE);
        if (hour>=0&&hour<6){
            hello.setText("深夜，现在的夜，熬的只是还未改变的习惯");
        }if (hour>=6&&hour<10){
            hello.setText("早安，清晨熹微的阳光，是你在微笑吗？");
        }if (hour>=10&&hour<16){
            hello.setText("午好，伴随着熟悉的乐曲，聆听着动人的旋律");
        }if (hour>=16&&hour<20){
            hello.setText("夕暮，似清风醉晚霞，不经意间盈笑回眸");
        }if (hour>=20){
            hello.setText("夜晚，一个安静的角落，静静的聆听夜曲");
        }
    }
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