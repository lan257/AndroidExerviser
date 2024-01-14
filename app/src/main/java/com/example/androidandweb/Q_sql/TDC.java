package com.example.androidandweb.Q_sql;
import android.content.SharedPreferences;
import java.io.IOException;
import java.util.List;
import java.util.function.BiFunction;

//对数据库表进行增删改查
public class TDC {

    select sel;
   //数据存入文件
    public void saveFile(String FileName,String tableName,List<Object> Data) throws IOException {

        // 获取SharedPreferences对象，参数为文件名和访问模式
        SharedPreferences fileName = mySql.getSharedPreferences(FileName);//取出文件
        SharedPreferences.Editor tofile=fileName.edit();//读取数据
        String data= sqlTool.ToFile(Data);
        tofile.putString(tableName,data);
        tofile.apply();
    }
    //读取数据
    public List<Object> get(String FileName,String tableName) throws IOException, ClassNotFoundException {
        // 获取SharedPreferences对象，参数为文件名和访问模式
        SharedPreferences fileName = mySql.getSharedPreferences(FileName);//取出文件
        String data= fileName.getString(tableName,"[]");
        return sqlTool.ToList(data);
    }
    //查
    public List<Object> S(String FileName, String tableName, Object x , BiFunction<Object,Object, Boolean> selectize) throws IOException, ClassNotFoundException {
        List<Object> Data=get(FileName,tableName);
        List<Object> selective = null;
        for (Object e:Data
             ) {
            if(selectize.apply(e,x)){
                selective.add(e);
            }
        }
        return selective;
    }
    //增
    public void I(String FileName,String tableName,Object i) throws IOException, ClassNotFoundException {
        List<Object> Data=get(FileName,tableName);
        boolean is=false;
        for (Object e:Data
             ) {
            if (e.equals(i)) {
                is = true;
                break;
            }}
        if (!is){Data.add(i);}
        //储存数据
        saveFile(FileName,tableName,Data);
    }
    //删
    public void D(String FileName,String tableName,Object x,BiFunction<Object,Object, Boolean> selectize) throws IOException, ClassNotFoundException {
        List<Object> Data=get(FileName,tableName);
        Data.removeIf(e -> selectize.apply(e,x));
        //写入文件
        saveFile(FileName,tableName,Data);
    }
    //改
    public void U(String FileName,String tableName,Object x ,Object y,BiFunction<Object,Object, Boolean> selectize,BiFunction<Object,Object,Object> Update) throws IOException,ClassNotFoundException{
        List<Object> Data=S(FileName,tableName,x,selectize);
        for (int i=0;i<Data.size();i++){
            Object e=Data.get(i);
            Object updatedValue= Update.apply(e,y);
            Data.set(i,updatedValue);
        }
        //写入文件
        saveFile(FileName,tableName,Data);
    }

}
