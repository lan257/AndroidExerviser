package com.example.androidandweb.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidandweb.O_solidObjects.simpleObjects.commit;
import com.example.androidandweb.O_solidObjects.simpleObjects.reply;
import com.example.androidandweb.R;
import com.example.androidandweb.http.ImageLoader;
import com.example.androidandweb.http.PackageHttp;

import java.util.List;
import java.util.Objects;

import lombok.NonNull;

public class comAdR extends RecyclerView.Adapter<comAdR.MyViewHolder> {
    private List<commit> itemList;
    private Context context;

    public comAdR(Context context, List<commit> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public comAdR.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.s_com, parent, false);
        return new comAdR.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull comAdR.MyViewHolder holder, int position) {
        commit item = itemList.get(position);
        holder.nickname.setText(item.getU().getNickname());
        holder.time.setText(new PackageHttp().formatTime(item.getTime()));
        holder.loveNum.setText(item.getLove()+"");
        if(!Objects.equals(item.getCom(), "")){
            holder.com.setVisibility(View.VISIBLE);
            holder.com.setText(item.getCom());}

        List<reply> replyList=item.GReply();
        //设置回复栏reply1,reply2,more

        //设置图片
        new ImageLoader().loadOnlineImage(context, new PackageHttp().toImgUrl(item.getU().getImg()), holder.userImg);
        if (!Objects.equals(item.getImg(), "")){
            holder.comImg.setVisibility(View.VISIBLE);
        new ImageLoader().loadOnlineImage(context, new PackageHttp().toImgUrl(item.getImg()), holder.comImg);}
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder内部静态类
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nickname,com,reply1,reply2,more,time,loveNum;
        public ImageView userImg,comImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.nickName);
            com=itemView.findViewById(R.id.comText);
            reply1=itemView.findViewById(R.id.reply1);
            reply2=itemView.findViewById(R.id.reply2);
            more=itemView.findViewById(R.id.moreReply);
            time=itemView.findViewById(R.id.time);
            loveNum=itemView.findViewById(R.id.loveNum);
            userImg=itemView.findViewById(R.id.Imager);
            comImg=itemView.findViewById(R.id.comImg);
        }
    }
}

