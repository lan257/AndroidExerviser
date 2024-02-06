package com.example.androidandweb.Activity;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.androidandweb.O_solidObjects.video;
import com.example.androidandweb.R;
import com.example.androidandweb.http.FileUploadTask;
import com.example.androidandweb.http.PackageHttp;
import com.example.androidandweb.http.postJwt;
import com.google.gson.Gson;

import java.util.Objects;

public class videoSubmit extends AppCompatActivity implements View.OnClickListener{
    video v=new video();
    private static final int PICK_IMAGE_REQUEST = 1;

    int imgIs=0;
    Uri selectedImageUri;
    String imgUrl5;
    int thingIs=0;
    int vid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_submit);
        Button submit=findViewById(R.id.AAWVSubmit);
        submit.setOnClickListener(this);
        Button update=findViewById(R.id.AAWVUpdate);
        update.setOnClickListener(this);
        Intent intent=getIntent();
        String thing=intent.getStringExtra("thing");
        if(Objects.equals(thing, "change")){
            imgIs=2;
            submit.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
            thingIs=1;
            vid=intent.getIntExtra("vid",1);
            vShow(vid);
            TextView title=findViewById(R.id.title);
            title.setText("申请修正资源");
        }
        Button VImgChange=findViewById(R.id.selectVImg);
        VImgChange.setOnClickListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // 在页面从后台返回前台时触发的操作
        // 例如，显示一个 Toast 消息

    }
    private void vShow(int vid) {
        video s = new video();
        s.setVid(vid);
        String url="/videoShow";
        postJwt.sendPostRequest(url, s, result -> {
            // 处理返回的Result对象
            if (result != null) {
                if (result.iu != 0) {//接收成功
                    String video = new Gson().toJson(result.data);
//                        Type type = new TypeToken<user>(){}.getType();
                    v = new Gson().fromJson(video, video.class);
                    TextView vName=findViewById(R.id.vName);
                    TextView vIntro=findViewById(R.id.vIntro);
//                        TextView vUrl=findViewById(R.id.vUrl);
                    vName.setText(v.getVName());
                    vIntro.setText(v.getVIntro());
//                        vUrl.setText(v.getVUrl());
                    ImageView img =findViewById(R.id.img);
                    PackageHttp packageHttp=new PackageHttp();
                    String imgUrl= packageHttp.toImgUrl(v.getImg()) ;
                    imgUrl5=v.getImg();
                    Log.i("图片",imgUrl);
                    Glide.with(videoSubmit.this).load(imgUrl).into(img);
                }
            }
        });
    }

    public void onClick(View v) {
        if (v.getId() == R.id.AAWVSubmit) {
            VSubmit("/vImgSubmit","/vSubmit");
            //上传视频资源
        }
        if (v.getId()==R.id.selectVImg){
            openGallery();
        }
        if (v.getId() == R.id.AAWVUpdate) {
            VSubmit("/vImgSubmit","/vUpdate");
            //修正视频资源
        }
    }

    public void VSubmit(String url1,String url2 ){
        EditText vName=findViewById(R.id.vName);
        v.setVName(vName.getText().toString());
        TextView vIntro=findViewById(R.id.vIntro);
        if(!vIntro.getText().toString().equals("")){
        v.setVIntro(vIntro.getText().toString());}
        CheckBox allow=findViewById(R.id.allowChange);
        if(allow.isChecked()){v.setIns(1);}else {v.setIns(0);}
        CheckBox uid=findViewById(R.id.showName);
        if(uid.isChecked()){v.setUid(1);}
        if (thingIs==1){
            v.setVid(vid);
        }
//        TextView vUrl=findViewById(R.id.vUrl);
//        if(vUrl.getText().toString()!=null){
//            v.setVUrl(vUrl.getText().toString());}
        if (imgIs==1){
            v.setImg(getRealPathFromURI(selectedImageUri));
            FileUploadTask fileUploadTask = new FileUploadTask(url1,v.getImg());
            fileUploadTask.execute();
        }
        if (imgIs==2){
            v.setImg(imgUrl5);
        }
        postJwt.sendPostRequest(url2, v, result -> {
            // 处理返回的Result对象
            if (result != null) {
                if(result.iu!=0){//上传成功
                    Toast.makeText(videoSubmit.this, "服务器上传成功", Toast.LENGTH_SHORT).show();}
                else {
                    Toast.makeText(videoSubmit.this, result.msg, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(videoSubmit.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //打开相册
    void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView imageView=findViewById(R.id.img);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);
            imgIs=1;
            // 在这里处理选择的图片
            // 可以获取图片路径 selectedImageUri.getPath() 或者使用其他方法
        }
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
}