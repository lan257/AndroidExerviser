package com.example.androidandweb.O_solidObjects.simpleObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class commit {
    int cid;
    int aid;
    int uid;
    String com;
    String img;
    int love;
    int replyNum;
    String reply;
}
