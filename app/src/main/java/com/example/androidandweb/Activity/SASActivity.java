package com.example.androidandweb.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidandweb.O_solidObjects.activity;
import com.example.androidandweb.O_solidObjects.simpleObjects.Result;
import com.example.androidandweb.O_solidObjects.simpleObjects.aCon;
import com.example.androidandweb.R;
import com.example.androidandweb.adapter.aConAd;
import com.example.androidandweb.http.FileUploadTask;
import com.example.androidandweb.http.postJwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SASActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int PICK_IMAGE_REQUEST = 1;
    List<aCon> aConList=new ArrayList<>();
    int changId=0;//操作修改的文本id；
    EditText updataText;
    int imgThing;

    aCon titleImg=new aCon(2,"/img/1e8d58b4b9746db74773ebab1d7cf90e1661172578520.jpeg",null);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sasactivity);
        goStart();
        setButtonClick();
    }
    //按钮设置点击事件
    private void setButtonClick() {
        Button addImg=findViewById(R.id.insertImg);
        addImg.setOnClickListener(this);
        Button Refreah=findViewById(R.id.Refresh);
        Refreah.setOnClickListener(this);
        Button submit =findViewById(R.id.submit);
        submit.setOnClickListener(this);
        Button showMore1 =findViewById(R.id.showMore1);
        showMore1.setOnClickListener(this);
        Button titleImg =findViewById(R.id.TitleImg);
        titleImg.setOnClickListener(this);
    }

    //按钮事件处理
    @Override
    public void onClick(View v) {
        LinearLayout more1=findViewById(R.id.more1);
        Button showMore1 =findViewById(R.id.showMore1);
    if (v.getId()==R.id.insertImg){
    more1.setVisibility(View.GONE);
    showMore1.setText("+");
    addImg();
}
    if(v.getId()==R.id.Refresh){
    more1.setVisibility(View.GONE);
    showMore1.setText("+");
    String changText= updataText.getText().toString();
    if (!changText.equals("")) {
        aConList.get(changId).setCon(changText);
    }
    showContextListView(aConList);
}
    if (v.getId()==R.id.submit){
    String changText= updataText.getText().toString();
    if (!changText.equals("")) {
        aConList.get(changId).setCon(changText);
    }
    showContextListView(aConList);
    makeSureAgain();//弹出确认窗口
}
    if (v.getId()==R.id.showMore1){

        if (more1.getVisibility()==View.VISIBLE) {
            more1.setVisibility(View.GONE);
            showMore1.setText("+");
        }else {
            more1.setVisibility(View.VISIBLE);
            showMore1.setText("-");
        }}
    if (v.getId()==R.id.TitleImg){
        more1.setVisibility(View.GONE);
        showMore1.setText("+");
        setImg();
    }
    }

    //添加图片
    private void setImg() {
        imgThing=3;
        //选择一张图片
        openGallery();
    }
    //确认上传弹窗
    private void makeSureAgain() {
        String title="确定上传",text="确定上传文件",select1="确认",select2="取消";
        new AlertDialog.Builder(SASActivity.this).setTitle(title).setMessage(text)
        .setPositiveButton(select1, (dialog, which) -> {//事件一
            ActivitySubmit();
        }).setNegativeButton(select2, (dialog, which) -> {//事件二
            Toast.makeText(SASActivity.this, "取消上传", Toast.LENGTH_SHORT).show();
        }).create().show();
    }
    //上传服务器
    private void ActivitySubmit() {

        //上传Activity图片内容
        imgListSubmit();
        //上传Activity数据库内容，即aConList
        aConListSubmit();

    }
    //文本上传服务器
    private void aConListSubmit() {
        aCon titleImg1=new aCon(titleImg.getType(),titleImg.getCon(),titleImg.getUri());
        titleImg1.conChange();
        Log.i("为什么",""+titleImg1);
        List<aCon> aConList1=new ArrayList<>();
        for (aCon a:aConList) {
            aConList1.add(new aCon(a.getType(),a.getCon(),a.getUri()));
        }
        for (aCon a:aConList1) {a.conChange();}
        EditText TitleText=findViewById(R.id.titleText);
        activity activity=new activity();
        activity.setTitleText(TitleText.getText().toString());
        activity.STitleImg(titleImg1);
        activity.SContent(aConList1);
        activity.SChangeTime();
        Log.i("为什么",""+activity);
        //设置标签
        postJwt.sendPostRequest("/aConListSubmit", activity, result -> {
            // 处理返回的Result对象
            if (result != null) {
                if(result.iu!=0){
                    Toast.makeText(SASActivity.this, result.msg, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SASActivity.this,index.class));
                }}});}
    //图片上传服务器
    private void imgListSubmit() {
        String url="/ActImgSubmit";
        //上传标题图片
        FileUploadTask fileUploadTask1 = new FileUploadTask(url,titleImg.getCon());
        fileUploadTask1.execute();
        //上传内容图片
        for (aCon a:aConList
        ) {
            if (a.getType()==2){
                FileUploadTask fileUploadTask = new FileUploadTask(url,a.getCon());
                fileUploadTask.execute();}
        }
    }
    //增加一张图片
    public void addImg(){
        imgThing=1;
        //选择一张图片
        openGallery();
    }
    //打开相册
    void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }
    //初始化操作
    private void goStart() {
        //判断来意做出判断
        //现在假如是发布活动
        aConList.add(new aCon(1,""));
        showContextListView(aConList);
        updataText=findViewById(R.id.updateText);
    }

    //更新和保存文本
    void changeText(){
        String changText= updataText.getText().toString();
        if (!changText.equals("")){
        aConList.get(changId).setCon(changText);
        updataText.setText("");}
    }
    //显示内容的ListView
    private void showContextListView(List<aCon> aCList) {
        aConAd adapter=new aConAd(SASActivity.this, R.layout.s_acon,aCList,changId);
        ListView listView=findViewById(R.id.ActivityContextShow);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            aCon ac= aCList.get(position);
            if (ac.getType()==2){
                imgThing=2;
                changId=position;
                openGallery();
            }return false;
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            aCon ac= aCList.get(position);
            if (ac.getType()==1){
                changeText();
                changId=position;
                updataText.setText(ac.getCon());
                showContextListView(aConList);
        }});
        int lastItem = adapter.getCount() - 1;
        if (lastItem >= 0) {
            listView.setSelection(changId);}}

    //相册选择结果处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if(imgThing==1){
                changeText();//保存已有内容
                if (Objects.equals(aConList.get(aConList.size() - 1).getCon(), "")){
            aConList.remove(aConList.size() - 1);}
            aConList.add(new aCon(2,getRealPathFromURI(selectedImageUri),selectedImageUri));
            //重置输入框为最新text
            changId=aConList.size();
            aConList.add(new aCon(1,""));
            showContextListView(aConList);
            // 在这里处理选择的图片
            // 可以获取图片路径 selectedImageUri.getPath() 或者使用其他方法
        }else if (imgThing==2){
                changeText();
                aConList.get(changId).setUri(selectedImageUri);
                aConList.get(changId).setCon(getRealPathFromURI(selectedImageUri));
                changId=aConList.size();
                showContextListView(aConList);
            } else if(imgThing==3){
                titleImg.setCon(getRealPathFromURI(selectedImageUri));
                titleImg.setUri(selectedImageUri);
                Log.i("为什么",""+titleImg);
        }}
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