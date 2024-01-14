package com.example.androidandweb.O_solidObjects;

import com.example.androidandweb.O_solidObjects.simpleObjects.privateUser;

import java.util.Objects;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class user {
//    UUID uid= UUID.randomUUID() ;
    int uid;
    String nikename;
    int type=2;
    privateUser privateUser;
    String email;//不可读必写可改
    String password;//不可读必写可改

    public user(String nikename, privateUser privateUser) {
        this.nikename = nikename;
        this.privateUser = privateUser;
    }

    public user(String nikename, String email, String password) {
        this.nikename = nikename;
        this.email = email;
        this.password = password;
    }

    public void update(user wen){
        this.nikename= wen.nikename==null ?this.nikename: wen.nikename;
        this.type= wen.type==1?wen.type:this.type;
        this.email= wen.email==null ?this.email: wen.email;
        this.password= wen.password==null ?this.password: wen.password;
    }
    public boolean select(user wen){
        if (this.uid==wen.uid){
            return true;
        }
        if (!Objects.equals(this.email, wen.email)&&wen.email!=null){
            return false;
        }
        if (!Objects.equals(this.nikename, wen.nikename)&&wen.nikename!=null){
            return false;
        }
        if (!Objects.equals(this.password, wen.password)&&wen.password!=null){
            return false;
        }
        if (!Objects.equals(this.type, wen.type)&&wen.type!=0){
            return false;
        }
        return true;
    }
}
