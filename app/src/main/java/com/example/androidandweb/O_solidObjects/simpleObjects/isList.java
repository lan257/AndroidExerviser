package com.example.androidandweb.O_solidObjects.simpleObjects;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;

public class isList {
    String is;
    String data;

    public isList(String is, String data) {
        this.is = is;
        this.data = data;
    }
    void update(isList wen){
        this.data=wen.data==null?this.data: wen.data;
        this.is= wen.is==null ?this.is: wen.is;
    }
    boolean select(isList wen){
        if (!Objects.equals(this.is, wen.is)&&wen.is!=null){
            return false;
        }
        if (!Objects.equals(this.data, wen.data)&&wen.is!=null){
            return false;
        }
        return true;
    }
}
