package com.example.androidandweb.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidandweb.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class videoShow extends AppCompatActivity {
    String videoUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Intent getUrl=getIntent();
        videoUrl=getUrl.getStringExtra("vUrl");

        SimpleExoPlayer player = new SimpleExoPlayer.Builder(this).build();
        PlayerView playerView =(PlayerView) findViewById(R.id.View_Player);

// 将Player关联到PlayerView
        playerView.setPlayer(player);

// 准备媒体资源
try {
    MediaItem mediaItem = MediaItem.fromUri(videoUrl);
    player.setMediaItem(mediaItem);
}catch (RuntimeException e){
    Toast.makeText(this, "视频资源似乎失效了", Toast.LENGTH_SHORT).show();
}


// 准备播放器
        player.prepare();

// 开始播放
      //  player.play();
    }
}