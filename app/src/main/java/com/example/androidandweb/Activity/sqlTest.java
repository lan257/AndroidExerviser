package com.example.androidandweb.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidandweb.G_SqlDao.musicDao;
import com.example.androidandweb.O_solidObjects.music;
import com.example.androidandweb.Q_sql.TDC;
import com.example.androidandweb.R;

import java.io.IOException;
import java.util.List;

public class sqlTest extends AppCompatActivity implements View.OnClickListener {
    musicDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_test);
        Button I=findViewById(R.id.I);
        Button S=findViewById(R.id.S);
        Button U=findViewById(R.id.U);
        Button D=findViewById(R.id.D);
        I.setOnClickListener(this);
        S.setOnClickListener(this);
        U.setOnClickListener(this);
        D.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.I) {
            music i=new music("天苍苍","http://www.天苍苍.com","西蚕");
            try {
                dao.insert("穹","music",i);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            //增加数据
        }
        if (v.getId() == R.id.S) {
       //     music s=new music();
//            try {
//                List<Object> AllMusic =dao.sel("穹","music",s);
//                Toast.makeText(sqlTest.this, ""+AllMusic, Toast.LENGTH_SHORT).show();
//            } catch (IOException | ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
            TDC tdc = new TDC();
            try {
                List<Object> AllMusic= tdc.get("穹","music");
                Toast.makeText(sqlTest.this, ""+AllMusic, Toast.LENGTH_SHORT).show();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            //查找数据

        }
        if (v.getId() == R.id.U) {
            music u=new music();
            u.setAuthor("穹");
            music x=new music();
            x.setName("天苍苍");
            try {
                dao.upd("穹","music",x,u);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            //更新数据
        }
        if (v.getId() == R.id.D) {
            music d=new music();
            d.setName("天苍苍");
            try {
                dao.del("穹","music",d);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            //删除数据
        }

    }
}