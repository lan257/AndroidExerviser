package com.example.androidandweb.G_SqlDao;

import com.example.androidandweb.Q_sql.TDC;

import java.io.IOException;
import java.util.List;

public class musicDao {


    TDC tdc;
    public boolean select(Object e ,Object x){
        com.example.androidandweb.O_solidObjects.music a= (com.example.androidandweb.O_solidObjects.music) e;
        com.example.androidandweb.O_solidObjects.music b= (com.example.androidandweb.O_solidObjects.music) x;
        return a.select(b);
    }
    public Object update(Object e ,Object x){
        com.example.androidandweb.O_solidObjects.music a= (com.example.androidandweb.O_solidObjects.music) e;
        com.example.androidandweb.O_solidObjects.music b= (com.example.androidandweb.O_solidObjects.music) x;
        a.update(b);
        return a;
    }

    //数据库增加方法
    public void insert(String FileName,String tableName,Object i) throws IOException, ClassNotFoundException {
        tdc.I(FileName,tableName,i);
    }
    //数据库删除方法
    public void del(String FileName,String tableName,Object x) throws IOException, ClassNotFoundException {
        tdc.D(FileName,tableName,x,(e,y) ->select(e,x) );
    }
    //数据库查询方法
    public List<Object> sel(String FileName, String tableName, Object x) throws IOException, ClassNotFoundException {
        return tdc.S(FileName,tableName,x,(e,y) ->select(e,x));
    }
    //数据库更新方法
    //a是约束条件，b是修改条件
    public void upd(String FileName,String tableName,Object a,Object b) throws IOException, ClassNotFoundException {
        tdc.U(FileName,tableName,a,b,(e,y) ->select(e,a),(e,z)->update(e,b));
    }
}
