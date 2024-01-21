package com.example.androidandweb.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidandweb.O_solidObjects.simpleObjects.Result;
import com.example.androidandweb.O_solidObjects.user;
import com.example.androidandweb.Q_sql.mySql;
import com.example.androidandweb.R;
import com.example.androidandweb.http.FileUploadTask;
import com.example.androidandweb.http.postNoJwt;

public class signIn extends AppCompatActivity implements View.OnClickListener{

    private static final int PICK_IMAGE_REQUEST = 1;
    Uri selectedImageUri;
    private mySql MyApp;
    // 获取SharedPreferences对象，参数为文件名和访问模式
    SharedPreferences localState = mySql.getSharedPreferences("LocalState");//本机状态文件
    SharedPreferences.Editor LocalState = localState.edit();

    SharedPreferences localUserMsg = mySql.getSharedPreferences("localUserMsg");//用户数据库
    SharedPreferences.Editor LSM = localUserMsg.edit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        TextView Vid=findViewById(R.id.textVid);//订阅框
        Vid.setOnClickListener(this);
        Button updateVid=findViewById(R.id.updateVid);//更新订阅
        updateVid.setOnClickListener(this);//设置事件
        Button sign=findViewById(R.id.AAWSign);
        sign.setOnClickListener(this);
        Button img=findViewById(R.id.selectImg);
        img.setOnClickListener(this);
    }
    //按钮事件判定
    public void onClick(View v){
        if(v.getId()==R.id.AAWSign){
           //调用注册方法
            sign();
        }
        if(v.getId()==R.id.updateVid){
            TextView Vid=findViewById(R.id.textVid);
            String vid=Vid.getText().toString();
            LocalState.putString("vid", vid);
            LocalState.apply();
            Toast.makeText(signIn.this, "更新成功", Toast.LENGTH_SHORT).show();
        }
        if(v.getId()==R.id.textVid){
            TextView Vid=findViewById(R.id.textVid);
            Vid.setText("");
        }
        if(v.getId()==R.id.selectImg){
            openGallery();
        }
    }
    public void sign(){
        CheckBox allow=findViewById(R.id.allowAll);
        if(allow.isChecked()){
        TextView nickName=findViewById(R.id.TextNickName);
        TextView email=findViewById(R.id.editTextEmailAddress);
        TextView password=findViewById(R.id.editTextPassword);
        String PUEmail=email.getText().toString();
        String PUPassword=password.getText().toString();
        String nickname=nickName.getText().toString();
        String filepath=getRealPathFromURI(selectedImageUri);
        user u=new user(nickname,PUEmail,PUPassword);
            Log.d("File Path", filepath);

            //aaw,传递服务器储存
        String url="/sign";
            FileUploadTask fileUploadTask = new FileUploadTask(url,filepath);
            fileUploadTask.execute();
            url="/sign1";
        postNoJwt.sendPostRequest(url, u, new postNoJwt.OnResultListener() {
            public void onResult(Result result) {
                // 处理返回的Result对象
                if (result != null) {
                    if(result.iu!=0){//注册成功
                        Toast.makeText(signIn.this, "服务器注册成功", Toast.LENGTH_SHORT).show();}
                    else {
                        Toast.makeText(signIn.this, result.msg, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(signIn.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        sp,保存到本机
        CheckBox SP=findViewById(R.id.allowSp);
        if(SP.isChecked()){

            //注册到本机
        }
    }else {
            Toast.makeText(signIn.this, "你需要勾选同意app的相关规定", Toast.LENGTH_SHORT).show();
        }
    }
    //打开相册
    void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }
    //获得用户选择的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView imageView=findViewById(R.id.img);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);

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