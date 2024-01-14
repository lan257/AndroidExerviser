package com.example.androidandweb.Q_sql;

import android.util.Log;

import com.example.androidandweb.O_solidObjects.simpleObjects.isList;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//序列化
public class sqlTool {

    // 序列化对象列表到字符串
    public static <T> String ToFile(List<T> objectList) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(objectList);
        objectOutputStream.close();
        return byteArrayOutputStream.toString("ISO-8859-1");
    }

    // 从字符串反序列化为对象列表
    public static <T> List<T> ToList(String serializedList) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedList.getBytes(StandardCharsets.ISO_8859_1));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        List<T> objectList = (List<T>) objectInputStream.readObject();
        objectInputStream.close();
        return objectList;
    }
    //将对象序列化为对象

}
