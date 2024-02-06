package com.example.androidandweb.O_solidObjects;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class user implements Serializable {
    int thing;
    boolean loveIs;
    int uid;
    String nickname;
    int type=2;
    String password;
    int fan;
    int concern;
    String create;
    String change;
    String img;
    String email;
    String proMotto;
    int love;


    public user(String email, String password) {
        this.email=email;
        this.password=password;
    }

    public user(String nickname, String email, String password) {
        this.email=email;
        this.password=password;
        this.nickname=nickname;
    }
}
