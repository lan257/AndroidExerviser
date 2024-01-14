package com.example.androidandweb.O_solidObjects.simpleObjects;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import lombok.Data;

@Data
public class privateUser {

    int uid;//不可读不可写不可改
    String email;//不可读必写可改
    String password;//不可读必写可改
    public privateUser(String email, String password) {
        this.email = email;
        this.password = password;
    }
    void update(privateUser wen){
        this.email= wen.email==null ?this.email: wen.email;
        this.password= wen.password==null ?this.password: wen.password;
    }
    boolean select(privateUser wen){
        if (!Objects.equals(this.uid, wen.uid)&&wen.uid!=0){
            return false;
        }
        if (!Objects.equals(this.email, wen.email)&&wen.email!=null){
            return false;
        }
        if (!Objects.equals(this.password, wen.password)&&wen.password!=null){
            return false;
        }
        return true;
    }

}
