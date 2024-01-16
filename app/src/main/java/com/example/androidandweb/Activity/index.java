package com.example.androidandweb.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidandweb.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.navigation.NavigationView;

import lombok.NonNull;

public class index extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout index;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        index=findViewById(R.id.index);
        ImageView mi=findViewById(R.id.userImg);
        mi.setOnClickListener(this);
        NavigationView navigationView = findViewById(R.id.left);
        ImageView imageView = findViewById(R.id.userImg);
        String imageUrl = "https://dmimg.5054399.com/allimg/2012nianli/qianxingba/24.jpg";


// 使用 Glide 或其他图片加载库加载在线图片
        Glide.with(this).load(imageUrl).into(imageView);


        SimpleExoPlayer player = new SimpleExoPlayer.Builder(this).build();
        PlayerView playerView = findViewById(R.id.playerView);

// 将Player关联到PlayerView
        playerView.setPlayer(player);

// 准备媒体资源
        MediaItem mediaItem = MediaItem.fromUri("https://sns-video-bd.xhscdn.com/spectrum/06f77ba84733159ec47e3c525fd7ca0f0eabdfb4");
        player.setMediaItem(mediaItem);

// 准备播放器
        player.prepare();

// 开始播放
        player.play();

        navigationView.setNavigationItemSelectedListener(item -> {
            // 在这里处理菜单项的点击事件
            if(item.getItemId()==R.id.select1){
        Log.i("","我点击了选项一");
        Toast.makeText(index.this, "选项一", Toast.LENGTH_SHORT).show();
    }

    if(item.getItemId()==R.id.select2){Log.i("","我点击了选项二");
        Toast.makeText(index.this, "选项二", Toast.LENGTH_SHORT).show();
    }if(item.getItemId()==R.id.select3){
        Toast.makeText(index.this, "选项三", Toast.LENGTH_SHORT).show();
    }if(item.getItemId()==R.id.select4){
        Toast.makeText(index.this, "选项四", Toast.LENGTH_SHORT).show();
    }
    return true;
        });

    }
    public void onClick(View v) {
        if(v.getId()==R.id.userImg){
            index.openDrawer(GravityCompat.START);
        }

    }

//
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.nav_menu,menu);
//        return true;
//    }


//    public boolean onOptionsItemSelected(MenuItem item){
//
//
//Log.i("","点击按钮让我看见");
//        Log.i("","我点击了"+item.getItemId());
//        if(item.getItemId()==R.id.select1){
//            Log.i("","我点击了选项一");
//            Toast.makeText(this, "选项一", Toast.LENGTH_SHORT).show();
//        }
//
//        if(item.getItemId()==R.id.select2){Log.i("","我点击了选项二");
//            Toast.makeText(this, "选项二", Toast.LENGTH_SHORT).show();
//        }if(item.getItemId()==R.id.select3){
//            Toast.makeText(this, "选项三", Toast.LENGTH_SHORT).show();
//        }if(item.getItemId()==R.id.select4){
//            Toast.makeText(this, "选项四", Toast.LENGTH_SHORT).show();
//        }
//        return true;
//    }

}