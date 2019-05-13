package com.example.esrapc.sqlliteornek;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListelemeActivity extends AppCompatActivity implements View.OnClickListener {

    Button isimBtn,telefonBtn,mailBtn;
    ListView rehber;
    VeriTabani vt;
    String ad,mail,tel;
    String nameStr, phoneStr, mailStr, numberStr;
    int id1;
    ArrayList<String> txt = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listeleme);

        rehber = findViewById(R.id.listViewRehber);

        //butonlara toplu tıklanma
        isimBtn = findViewById(R.id.isimSiralaBtn);
        isimBtn.setOnClickListener(this);

        telefonBtn = findViewById(R.id.telefonSiralaBtn);
        telefonBtn.setOnClickListener(this);

        mailBtn = findViewById(R.id.mailSiralaBtn);
        mailBtn.setOnClickListener(this);

        //veritabanını çağrıyoruz
        vt = new VeriTabani(this);


    }

    @Override
    public void onClick(View view) {
        //vt yi açıyoruz
        SQLiteDatabase db = vt.getWritableDatabase();

        String orderBy = null;

        //vt yi konumlandırmak için cursor
        Cursor cursor = null;

        switch(view.getId())
        {
            case R.id.isimSiralaBtn: orderBy = "ISIM"; break; //vt deki isimlere göre
            case R.id.telefonSiralaBtn: orderBy = "TEL_NO"; break;
            case R.id.mailSiralaBtn: orderBy = "EMAIL" ; break;
        }
        cursor = db.query("KAYIT",null,null,null,null,null,orderBy);
txt.clear();
        //okudukça sırakine hareket et
        while (cursor.moveToNext()){
            ad = cursor.getString(cursor.getColumnIndex("ISIM"));
            id1 = cursor.getInt(cursor.getColumnIndex("ID"));
            tel = cursor.getString(cursor.getColumnIndex("TEL_NO"));
            mail = cursor.getString(cursor.getColumnIndex("EMAIL"));

            txt.add(id1+"\t"+ad+"\t"+tel+"\t"+mail);

        }
        //vt yi ve cursor ı kapatıyoruz
        cursor.close();
        db.close();

        ArrayAdapter<String> veriAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,txt);
        rehber.setAdapter(veriAdapter);

        rehber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //verileri parçalara ayırarak, veri dizisine aktarıyoruz
                String[] veri = txt.get(i).split("\t");

                Intent sendData = new Intent(getApplicationContext(), ResultActivity.class);
                sendData.putExtra("ID", veri[0]);
                sendData.putExtra("ISIM", veri[1]);
                sendData.putExtra("TEL_NO", veri[2]);
                sendData.putExtra("EMAIL", veri[3]);
                startActivity(sendData);
            }
        });
    }
}
