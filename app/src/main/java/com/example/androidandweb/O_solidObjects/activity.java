package com.example.androidandweb.O_solidObjects;
import com.example.androidandweb.O_solidObjects.simpleObjects.aCon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.androidandweb.O_solidObjects.simpleObjects.commit;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class activity {
    int aid;
    int uid;
    String changeTime;//DateTime转化成String
    String createTime;//读取DateTime
    String context;//aCon类的列表
    String titleText;
    String titleImg;//一张图片的url
    String commit;//commit类的列表
    String finCommit;//commit类
    String type;//标签类



    //注入修改时间
    public void SChangeTime(){
        changeTime= LocalDateTime.now().toString();
    }
    //设置活动内容
    public void SContext(List<aCon> aConList){
        context=new Gson().toJson(aConList);
    }
    //获得活动内容
    public List<aCon> GContext(){
        return new Gson().fromJson(context, new TypeToken<List<aCon>>(){}.getType());
    }
    //设置评论内容
    public void SCommit(List<commit> commitList){
        context=new Gson().toJson(commitList);
    }
    //获得评论内容
    public List<commit> GCommit(){
        return new Gson().fromJson(commit, new TypeToken<List<commit>>(){}.getType());
    }
    //添加评论内容
    public void addCommit(commit con){
        List<commit> commitList=GCommit();
        commitList.add(con);
        SCommit(commitList);
    }
    //设置标题图片
    public void STitleImg(aCon aCon){
        titleImg= new Gson().toJson(aCon);
    }
    //获得标题图片
    public aCon GTitleImg(){
        return new Gson().fromJson(titleImg, new TypeToken<aCon>(){}.getType());
    }
    //设置最新评论
    public void SFinCommit(commit commit){
        finCommit= new Gson().toJson(commit);
    }
    //获得最新评论
    public commit GFinCommit(){
        return new Gson().fromJson(finCommit, new TypeToken<commit>(){}.getType());
    }
}
