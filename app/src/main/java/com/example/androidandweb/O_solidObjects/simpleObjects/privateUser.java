package com.example.androidandweb.O_solidObjects.simpleObjects;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    public String toJsonString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
