package com.example.androidandweb.Activity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidandweb.O_solidObjects.activity;
import com.example.androidandweb.O_solidObjects.lOperator;
import com.example.androidandweb.O_solidObjects.simpleObjects.aCon;
import com.example.androidandweb.O_solidObjects.simpleObjects.commit;
import com.example.androidandweb.Q_sql.mySql;
import com.example.androidandweb.R;
import com.example.androidandweb.adapter.aConAd;
import com.example.androidandweb.adapter.comAdR;
import com.example.androidandweb.http.FileUploadTask;
import com.example.androidandweb.http.ImageLoader;
import com.example.androidandweb.http.PackageHttp;
import com.example.androidandweb.http.postJwt;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class showAct extends AppCompatActivity implements View.OnClickListener{
    private static final int PICK_IMAGE_REQUEST = 1;
    activity act=new activity();
    String url;
    ImageView love;
    int aid,x=0,y=0,z=0,m=0;
    TextView comUnfold;
    Type type;
    commit commit=new commit();
    List<commit> commitList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        setButtonClick();
        actShow();
        getCom();
        Log.i("奇怪",act+"");
    }

    private void setButtonClick() {
        Intent intent=getIntent();
        Log.i("activityAid",""+intent.getIntExtra("aid",1));
        aid=intent.getIntExtra("aid",1);
        comUnfold=findViewById(R.id.comUnfold);
        setClick(R.id.comUnfold);
        setClick(R.id.sentCom);
        setClick(R.id.addImg);
        setClick(R.id.add);
        setClick(R.id.delImg);
        setClick(R.id.byLove);
        setClick(R.id.byTime);
        setClick(R.id.onlyUser);
        setClick(R.id.max);
        setClick(R.id.love);
    }

    @Override
    public void onClick(View v) {
        showS(v,R.id.byLove);
        showS(v,R.id.byTime);
        showS(v,R.id.onlyUser);
        if (isClick(v,R.id.comUnfold)&&x==1){
            comController();//评论区折叠
        }
        if (isClick(v,R.id.max)){
            changeMax();
        }
        if (isClick(v,R.id.sentCom)){
            sentCom();//发送评论
        }
        if (isClick(v,R.id.add)){
            unfold();//输入框扩展
        }
        if (isClick(v,R.id.addImg)){
            addImg();//添加图片
        }
        if (isClick(v, R.id.love)) {
            actAddLove();
        }
        if (isClick(v,R.id.delImg)){commit.setImg("");findViewById(R.id.showImg).setVisibility(View.GONE);}
    }

    private void actAddLove() {
        lOperator lp=new lOperator(act.getAid(),2,1,0);
        if (act.isLoveIs()){
            url = "/Love--";
            postJwt.sendPostRequest(url,lp, result1 -> {
                // 处理返回的Result对象
                if (result1 != null) {
                    if (result1.iu!=0) {
                        act.setLoveIs(false);
                        Glide.with(this).load(R.drawable.love).into(love);
                        Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
                    }}});}
        else {
            String url1 = "/Love++";
            postJwt.sendPostRequest(url1,lp, result1 -> {
                // 处理返回的Result对象
                if (result1 != null) {
                    if (result1.iu!=0) {
                        act.setLoveIs(true);
                        Glide.with(this).load(R.drawable.loved).into(love);
                        Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();}
                }});
        }
    }

    private void showS(View v,int s) {
        if (isClick(v,s)){
        findViewById(R.id.byTime).setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        findViewById(R.id.byLove).setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        findViewById(R.id.onlyUser).setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        findViewById(s).setBackgroundColor(ContextCompat.getColor(this, R.color.s111));}
    }

    //加载活动
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
    //加载评论区
    private void getCom() {
        String url = "/getComList";
        postJwt.sendPostRequest(url, act, result -> {
            // 处理返回的Result对象
            if (result != null) {
                if (result.iu!=0) {
                    type = new TypeToken<List<commit>>(){}.getType();
                    commitList = new Gson().fromJson(new Gson().toJson(result.data), type);
                    RecyclerView recyclerView=findViewById(R.id.comShow);
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
                    comAdR adapter = new comAdR(this, commitList);
                    adapter.setOnItemClickListener(position -> {
                        commit item =commitList.get(position);
                        lOperator lp=new lOperator(item.getCid(),3,1,0);
                        if (item.isLike()){
                            String url1 = "/Love--";
                            postJwt.sendPostRequest(url1,lp, result1 -> {
                                // 处理返回的Result对象
                                if (result1 != null) {
                                    if (result1.iu!=0) {
                                        getCom();
                                        Toast.makeText(this, "取消点赞", Toast.LENGTH_SHORT).show();
                                    }}});}
                        else {
                            String url1 = "/Love++";
                            postJwt.sendPostRequest(url1,lp, result1 -> {
                                // 处理返回的Result对象
                                if (result1 != null) {
                                    if (result1.iu!=0) {
                                        getCom();
                                        Toast.makeText(this, "点赞成功", Toast.LENGTH_SHORT).show();}
                                }});}
                        // 处理按钮点击事件
                        // position 表示点击的按钮所在的列表项位置
                    });
                    recyclerView.setAdapter(adapter);
                    findViewById(R.id.byLove).setBackgroundColor(ContextCompat.getColor(this, R.color.s111));
                }}});
    }
    //发送评论
    private void sentCom() {
        TextView chatText= findViewById(R.id.chatMsg);
        String text=chatText.getText().toString();
        if (!(text + commit.getImg()).equals("")){
            commit.setCom(text);
            commit.setAid(aid);
            if (!Objects.equals(commit.getImg(), "")){
            url="/sentComImg";
            FileUploadTask fileUploadTask1 = new FileUploadTask(url,commit.getImg());
            fileUploadTask1.execute();
            String pro= mySql.getSharedPreferences("LocalState").getInt("uid", 1)+"---";
            String con=new PackageHttp().changeFileName(commit.getImg(),pro,"comImg");
            commit.setImg(con);}
            url="/sentCom";
            postJwt.sendPostRequest(url,commit, result -> {
                // 处理返回的Result对象
                if (result != null) {
                    if (result.iu!=0) {
                        Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
                        chatText.setText("");
                        findViewById(R.id.showImg).setVisibility(View.GONE);
                        commit =new commit();
                        //刷新评论区
                        getCom();
                    }}});
        }
    }

    //处理图片选择
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LinearLayout linearLayout=findViewById(R.id.showImg);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            findViewById(R.id.showImg).setVisibility(View.VISIBLE);
            new ImageLoader().loadOnlineImage(this, getRealPathFromURI(selectedImageUri), linearLayout);
            commit.setImg(getRealPathFromURI(selectedImageUri));
            // 在这里处理选择的图片
            // 可以获取图片路径 selectedImageUri.getPath() 或者使用其他方法
        }
    }
    @SuppressLint("SetTextI18n")
    private void addImg() {
        if(Objects.equals(commit.getImg(), "")){
        openGallery();}else {
            String title="只能选择一张图片",text="是否选择替换现有图片",select1="确认",select2="取消";
            new AlertDialog.Builder(showAct.this).setTitle(title).setMessage(text)
                    .setPositiveButton(select1, (dialog, which) -> {//事件一
                        openGallery();
                    }).setNegativeButton(select2, (dialog, which) -> {//事件二
                    }).create().show();
        }
    }
    //展示活动内容
    private void showActList() {
        List<aCon> aConList= act.GContent();
//        Log.i("初始化失败",""+act);
        aConAd adapter=new aConAd(showAct.this, R.layout.s_acon,aConList,-1);
        ListView listView=findViewById(R.id.actShow);
        listView.setAdapter(adapter);
        setHigh();
    }

    //加载活动简单信息
    @SuppressLint("SetTextI18n")
    private void showSimple() {
        //头像展示
        ImageView imageView=findViewById(R.id.youImger);Glide.with(this).load(new PackageHttp().toImgUrl(act.getU().getImg())).circleCrop().into(imageView);
        love=findViewById(R.id.love);
        if(act.isLoveIs()){Glide.with(this).load(R.drawable.loved).into(love);}
        //昵称展示
        TextView nickname= findViewById(R.id.user_nickname);nickname.setText(act.getU().getNickname());
        TextView titleText= findViewById(R.id.titleText);titleText.setText(act.getTitleText());
        TextView time= findViewById(R.id.createTime);time.setText("发布于："+new PackageHttp().formatTime(act.getCreateTime()) +"  编辑于:"+new PackageHttp().formatTime(act.getChangeTime()));
    }
    //展开评论区
    private void comController() {
        if (y==0){
            comUnfold.setText("收起");
            y=1;//评论区打开
            final LinearLayout linearLayout = findViewById(R.id.xHigh);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams2.height=layoutParams2.height/2;
            linearLayout.setLayoutParams(layoutParams2);}
        else if (y==1){
            comUnfold.setText("评论区");
            y=0;//评论区合闭
            final LinearLayout linearLayout = findViewById(R.id.xHigh);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams2.height=layoutParams2.height*2;
            linearLayout.setLayoutParams(layoutParams2);}
    }
    //展开发布评论扩展框
    private void unfold() {
        ImageView add= findViewById(R.id.add);
        if (z==0){findViewById(R.id.AddUnfold).setVisibility(View.VISIBLE);z=1;
            add.setImageResource(R.drawable.tra3);}else {
            findViewById(R.id.AddUnfold).setVisibility(View.GONE);z=0;
            add.setImageResource(R.drawable.add);
        }
    }
    //最大化评论区
    private void changeMax() {
        if (m==0){
            final LinearLayout linearLayout = findViewById(R.id.xHigh);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            m=layoutParams2.height;
            layoutParams2.height=0;
            linearLayout.setLayoutParams(layoutParams2);
            TextView max= findViewById(R.id.max);
            max.setText("恢复");}
        else {
            final LinearLayout linearLayout = findViewById(R.id.xHigh);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams2.height=m;
            m=0;
            linearLayout.setLayoutParams(layoutParams2);
            TextView max= findViewById(R.id.max);
            max.setText("最大化");
        }
    }
    //判定点击属性
    private boolean isClick(View v,int s){
        return v.getId()==s;
    }
    //快捷设置点击监听
    private void setClick(int viewId) {
        View view=findViewById(viewId);
        view.setOnClickListener(this);
    }
    //打开系统相册
    void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    //图片url转化为filePath
    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }
        return null;
    }
    //元素限高
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
                    x=1;//文章很长
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