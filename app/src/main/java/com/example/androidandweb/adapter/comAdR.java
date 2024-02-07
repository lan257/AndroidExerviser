package com.example.androidandweb.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
//    private lOperator lp;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onButtonClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
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
        new PackageHttp();
        holder.time.setText(PackageHttp.formatTime(item.getTime()));
        holder.loveNum.setText(item.getLove()+"");
        if(!Objects.equals(item.getCom(), "")){
            holder.com.setVisibility(View.VISIBLE);
            holder.com.setText(item.getCom());}

        List<reply> replyList=item.GReply();
        //设置回复栏reply1,reply2,more

        //设置图片
//        new ImageLoader().loadOnlineImage(context, new PackageHttp().toImgUrl(item.getU().getImg()), holder.userImg);
        Glide.with(context).load(new PackageHttp().toImgUrl(item.getU().getImg())).circleCrop().into(holder.userImg);
        if (!Objects.equals(item.getImg(), "")){
            holder.comImg.setVisibility(View.VISIBLE);
            Glide.with(context).load(new PackageHttp().toImgUrl(item.getImg())).into(holder.comImg);}
        //设置点赞图片
        if (item.isLike()){holder.love.setImageResource(R.drawable.liked);}else {holder.love.setImageResource(R.drawable.like);}
        //为评论点赞
        holder.love.setOnClickListener(v -> {if (onItemClickListener!=null){onItemClickListener.onButtonClick(position);}});
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder内部静态类
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nickname,com,reply1,reply2,more,time,loveNum;
        public ImageView userImg,comImg,love;

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
            love=itemView.findViewById(R.id.love);
        }
    }
}

