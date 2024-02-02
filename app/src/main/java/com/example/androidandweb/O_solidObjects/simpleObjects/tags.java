package com.example.androidandweb.O_solidObjects.simpleObjects;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class tags {
    String tagText;
    String[] nowTag={"科技","生活","游戏","影视","动漫","战争","咨询","问答"};
    List<String> tag=new ArrayList<>();
    public void ListAdd(String tagSmail){
        tag.add(tagSmail);
    }
    public void setTags(){
        tagText=new Gson().toJson(tag);
    }
}
