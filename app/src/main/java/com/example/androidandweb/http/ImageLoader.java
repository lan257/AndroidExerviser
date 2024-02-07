package com.example.androidandweb.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.androidandweb.R;

import lombok.NonNull;

public class ImageLoader {

    @SuppressLint("CheckResult")
    public void loadOnlineImage(Context context, String imageUrl, View view) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.no); // 加载过程中显示的占位图，可选
        requestOptions.error(R.drawable.no); // 加载失败时显示的图片，可选

        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(
                            @Nullable GlideException e,
                            Object model,
                            Target<Drawable> target,
                            boolean isFirstResource
                    ) {
                        // 加载失败的处理
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(
                            Drawable resource,
                            Object model,
                            Target<Drawable> target,
                            DataSource dataSource,
                            boolean isFirstResource
                    ) {
                        // 图片加载成功的处理
                        return false;
                    }
                })
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(
                            @NonNull Drawable resource,
                            @Nullable Transition<? super Drawable> transition
                    ) {
                        // 根据 View 类型设置背景
                        if (view instanceof ImageView) {
                            ((ImageView) view).setImageDrawable(resource);
                        } else if (view instanceof LinearLayout) {
                            view.setBackground(resource);
                        }
                        // 在这里，你可以根据需要添加其他类型的 View 的处理逻辑
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // 图片加载被取消时的处理
                    }
                });
    }
}