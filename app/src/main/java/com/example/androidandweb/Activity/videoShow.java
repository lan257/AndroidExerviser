package com.example.androidandweb.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidandweb.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

public class videoShow extends AppCompatActivity{
    String videoUrl;
    SimpleExoPlayer player;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;
//    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        b=findViewById(R.id.level);
//        b.setOnClickListener(this);
        Intent getUrl = getIntent();
        videoUrl = getUrl.getStringExtra("vUrl");
        int type = getUrl.getIntExtra("type", 0);
        PlayerView playerView = findViewById(R.id.View_Player);

        // 初始化 player，并将其关联到 PlayerView
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // 使用 DefaultMediaSourceFactory 创建默认的媒体源工厂
        DefaultMediaSourceFactory mediaSourceFactory = new DefaultMediaSourceFactory(this);

        try {
            if (type == 3) {
                // 设置媒体源工厂到 ExoPlayer
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, "缘风");

                MediaSource mediaSource = new HlsMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(videoUrl));

                player.setMediaSource(mediaSource);
            } else if (type == 2 || type == 1) {
                // 准备媒体资源
                MediaItem mediaItem = MediaItem.fromUri(videoUrl);
                player.setMediaItem(mediaItem);
            }

            // 恢复播放状态
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);

            // 准备媒体资源
            player.prepare();
        } catch (RuntimeException e) {
            Log.e("ExoPlayerError", "Error initializing player", e);
            Toast.makeText(this, "视频资源似乎失效了", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在页面暂停时保存播放状态
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.setPlayWhenReady(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在页面销毁时释放播放器资源
        if (player != null) {
            player.release();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 保存播放状态
        outState.putLong("playbackPosition", playbackPosition);
        outState.putInt("currentWindow", currentWindow);
        outState.putBoolean("playWhenReady", playWhenReady);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // 恢复播放状态
        playbackPosition = savedInstanceState.getLong("playbackPosition");
        currentWindow = savedInstanceState.getInt("currentWindow");
        playWhenReady = savedInstanceState.getBoolean("playWhenReady");
    }
    @Override
    protected void onResume() {
        super.onResume();
        // 在页面恢复时恢复播放
        if (player != null) {
            player.setPlayWhenReady(true);
        }
    }


}
