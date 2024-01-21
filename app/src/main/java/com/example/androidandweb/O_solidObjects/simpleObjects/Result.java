package com.example.androidandweb.O_solidObjects.simpleObjects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor//无参构造函数
@AllArgsConstructor//全参构造函数
public class Result {
    public int iu;//如果数据正确且没有任何问题，iu=1,有任何问题iu=0

    public String msg;//提示信息，比如登录功能，msg=“登陆成功”，
    // 因为密码登陆失败，msg="账号或密码错误",比如返回的数据是用户email.msg="这是用户email"
    public Object data;//返回的数据，比如返回用户全部信息，可以把用户信息封装成一个实体user，
    // data=new user;同理可以等于一个基本数据类型，一个列表，一个实体，一个文件等等。出现问题视情况赋值

}
