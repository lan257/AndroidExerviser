package com.example.androidandweb.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidandweb.O_solidObjects.activity;
import com.example.androidandweb.R;
import com.example.androidandweb.http.ImageLoader;
import com.example.androidandweb.http.PackageHttp;

import java.util.List;

import lombok.NonNull;

import android.widget.LinearLayout;
import android.widget.TextView;

public class actAdR extends RecyclerView.Adapter<actAdR.MyViewHolder> {
    private List<activity> itemList;
    private Context context;

    public actAdR(Context context, List<activity> itemList) {
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
        holder.userNicknameTextView.setText("作者:"+item.getU().getNickname());
        holder.com.setText(""+item.getCom());
        holder.love.setText(item.getLove()+"");
        String img = new PackageHttp().toImgUrl(item.GTitleImg().getCon());
        new ImageLoader().loadOnlineImage(context, img, holder.titleImg);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder内部静态类
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView,userNicknameTextView,com,love;
        public LinearLayout titleImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            userNicknameTextView = itemView.findViewById(R.id.userNickname);
            titleImg = itemView.findViewById(R.id.TitleImg);
            com=itemView.findViewById(R.id.com);
            love=itemView.findViewById(R.id.love);
        }
    }
}
