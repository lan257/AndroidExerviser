package com.example.androidandweb.Activity;


import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidandweb.O_solidObjects.activity;
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
    int aid,x=0,y=0,z=0,z1;
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
    }

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
                    recyclerView.setAdapter(adapter);}}});
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
    }

    @Override
    public void onClick(View v) {
        if (isClick(v,R.id.comUnfold)&&x==1){
            comController();//评论区折叠
        }
        if (isClick(v,R.id.sentCom)){
            sentCom();//发送评论
        }
        if (isClick(v,R.id.add)){
            unfold();
        }
        if (isClick(v,R.id.addImg)){
            addImg();
        }
        if (isClick(v,R.id.delImg)){commit.setImg("");findViewById(R.id.showImg).setVisibility(View.GONE);}
    }

    private void addImg() {
        openGallery();
    }



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
        Log.i("几点了",act.getCreateTime());
        TextView time= findViewById(R.id.createTime);time.setText("发布于："+new PackageHttp().formatTime(act.getCreateTime()) +"  编辑于:"+new PackageHttp().formatTime(act.getChangeTime()));
    }
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
    private void unfold() {
        ImageView add= findViewById(R.id.add);
        if (z==0){findViewById(R.id.AddUnfold).setVisibility(View.VISIBLE);z=1;
            add.setImageResource(R.drawable.tra3);}else {
            findViewById(R.id.AddUnfold).setVisibility(View.GONE);z=0;
            add.setImageResource(R.drawable.add);
        }
    }
    private boolean isClick(View v,int s){
        return v.getId()==s;
    }
    private void setClick(int viewId) {
        View view=findViewById(viewId);
        view.setOnClickListener(this);
    }
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