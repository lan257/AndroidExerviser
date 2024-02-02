package com.example.androidandweb.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.androidandweb.O_solidObjects.activity;
import com.example.androidandweb.R;
import com.example.androidandweb.http.PackageHttp;

import java.util.List;

import lombok.NonNull;

public class actAd extends RecyclerView.Adapter<MyViewHolder> {
    private List<activity> itemList;
    private Context context;

    public actAd(Context context, List<activity> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.s_activity, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        activity item = itemList.get(position);

        holder.titleTextView.setText(item.getTitleText());
        holder.userNicknameTextView.setText(item.getUid()+"");
        Log.i("图片url",new PackageHttp().toImgUrl(item.GTitleImg().getCon()));
        Glide.with(context).load(new PackageHttp().toImgUrl(item.GTitleImg().getCon())).into(holder.titleImg);
        //展示图片
//        Glide.with(context)
//                .asBitmap()
//                .load(new PackageHttp().toImgUrl(new PackageHttp().toImgUrl(item.GTitleImg().getCon())))
//                .into(new CustomTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
//                        holder.titleImg.setBackground(new BitmapDrawable(resource));
//                    }
//
//                    @Override
//                    public void onLoadCleared(Drawable placeholder) {
//                        // Do nothing
//                    }
//                });
    }
        @Override
        public int getItemCount() {
            return itemList.size();
        }
    }


