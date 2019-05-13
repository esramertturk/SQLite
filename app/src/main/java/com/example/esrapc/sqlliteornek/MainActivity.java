package com.example.esrapc.sqlliteornek;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button add, read, delete, clear, list, deleteAll, update, readShow;
    EditText name, phone, mail, number;
    String nameStr, phoneStr, mailStr, numberStr;
    VeriTabani vt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.etName);
        phone = findViewById(R.id.etPhone);
        mail = findViewById(R.id.etEmail);
        number = findViewById(R.id.etNumber);

        vt = new VeriTabani(this);

        add = findViewById(R.id.addBtn);
        read = findViewById(R.id.readBtn);
        delete = findViewById(R.id.deleteBtn);
        clear = findViewById(R.id.clearBtn);
        list = findViewById(R.id.listBtn);
        deleteAll = findViewById(R.id.deleteAllBtn);
        update = findViewById(R.id.updateBtn);
        readShow = findViewById(R.id.readShowBTn);

        add.setOnClickListener(this);
        read.setOnClickListener(this);
        delete.setOnClickListener(this);
        clear.setOnClickListener(this);
        list.setOnClickListener(this);
        deleteAll.setOnClickListener(this);
        update.setOnClickListener(this);
        readShow.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        //vt ye aktarırken ilk buna aktar sonra vt ye koy
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase sqLiteDatabase = vt.getWritableDatabase();      //vtmizi yazma işlemi için açtık
        //okumalarda kullanıcak alanlar
        String secim = null;
        String secimAra[] = null;

        switch (view.getId()) {
            case R.id.addBtn:
                //yeşil yerler tablodaki sütun adları aynı olmalı
                contentValues.put("ISIM", name.getText().toString());
                contentValues.put("TEL_NO", phone.getText().toString());
                contentValues.put("EMAIL", mail.getText().toString());
                long rowID = sqLiteDatabase.insert("KAYIT", null, contentValues); //tablomuza verileri aktar
                Toast.makeText(this, ":" + rowID + "nolu kayıt yapıldı", Toast.LENGTH_SHORT).show();
                break;
            case R.id.readBtn:
                String snameStr = name.getText().toString();
                String sphoneStr = phone.getText().toString();
                String smailStr = mail.getText().toString();
                String snumberStr = number.getText().toString();
                if (snumberStr.length() != 0) {
                    secim = "ID= ?";
                    secimAra = new String[]{snumberStr};
                } else if (snameStr.length() != 0) {
                    secim = "ISIM= ?";
                    secimAra = new String[]{snameStr};
                } else if (sphoneStr.length() != 0) {
                    secim = "TEL_NO= ?";
                    secimAra = new String[]{sphoneStr};
                } else if (smailStr.length() != 0) {
                    secim = "EMAIL= ?";
                    secimAra = new String[]{smailStr};
                }

                Cursor cursor = sqLiteDatabase.query("KAYIT", null, secim, secimAra, null, null, null);
                if (cursor.moveToFirst()) {
                    int siraIndex = cursor.getColumnIndex("ID");
                    Toast.makeText(this, "id:  " + siraIndex, Toast.LENGTH_SHORT).show();
                    numberStr = cursor.getString(siraIndex);
                    int adIndex = cursor.getColumnIndex("ISIM");
                    nameStr = cursor.getString(adIndex);
                    int telIndex = cursor.getColumnIndex("TEL_NO");
                    phoneStr = cursor.getString(telIndex);
                    int mailIndex = cursor.getColumnIndex("EMAIL");
                    mailStr = cursor.getString(mailIndex);
                    //  Toast.makeText(this, "" + numberStr + nameStr + phoneStr + mailStr, Toast.LENGTH_SHORT).show();
                    Intent sendData = new Intent(getApplicationContext(), ResultActivity.class);
                    sendData.putExtra("ID", numberStr);
                    sendData.putExtra("ISIM", nameStr);
                    sendData.putExtra("TEL_NO", phoneStr);
                    sendData.putExtra("EMAIL", mailStr);
                    startActivity(sendData);
                }
                break;
            //id si verileni sil
            case R.id.deleteBtn:
                snumberStr = number.getText().toString();
                int silSiraNo = sqLiteDatabase.delete("KAYIT", "ID = " + numberStr, null);
                if (silSiraNo == 0)
                    Toast.makeText(this, "Aranan kayıt dosyada yok.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, snumberStr + "nolu kayıt silindi", Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Silinen ksyıt: "+silSiraNo, Toast.LENGTH_SHORT).show();
                break;
            case R.id.clearBtn:
                number.setText(null);
                name.setText(null);
                phone.setText(null);
                mail.setText(null);
                break;
            case R.id.listBtn:
                Intent listIntent= new Intent(getApplicationContext(),ListelemeActivity.class);
                startActivity(listIntent);
                break;
            //tüm kayıtları sil
            case R.id.deleteAllBtn:
                int silinenKayit = sqLiteDatabase.delete("KAYIT", null, null);
                Toast.makeText(this, "Silinen kayıt sayısı: " + silinenKayit, Toast.LENGTH_SHORT).show();
                break;
            case R.id.readShowBTn:
                snameStr = name.getText().toString();
                sphoneStr = phone.getText().toString();
                smailStr = mail.getText().toString();
                snumberStr = number.getText().toString();
                if (snumberStr.length() != 0) {
                    secim = "ID= ?";
                    secimAra = new String[]{snumberStr};
                } else if (snameStr.length() != 0) {
                    secim = "ISIM= ?";
                    secimAra = new String[]{snameStr};
                } else if (sphoneStr.length() != 0) {
                    secim = "TEL_NO= ?";
                    secimAra = new String[]{sphoneStr};
                } else if (smailStr.length() != 0) {
                    secim = "EMAIL= ?";
                    secimAra = new String[]{smailStr};
                }

                cursor = sqLiteDatabase.query("KAYIT", null, secim, secimAra, null, null, null);
                if (cursor.moveToFirst()) {
                    int siraIndex = cursor.getColumnIndex("ID");
                    Toast.makeText(this, "id:  " + siraIndex, Toast.LENGTH_SHORT).show();
                    numberStr = cursor.getString(siraIndex);
                    int adIndex = cursor.getColumnIndex("ISIM");
                    nameStr = cursor.getString(adIndex);
                    int telIndex = cursor.getColumnIndex("TEL_NO");
                    phoneStr = cursor.getString(telIndex);
                    int mailIndex = cursor.getColumnIndex("EMAIL");
                    mailStr = cursor.getString(mailIndex);

                    number.setText(numberStr);
                    name.setText(nameStr);
                    mail.setText(mailStr);
                    phone.setText(phoneStr);
                }
                break;

            case R.id.updateBtn:
                contentValues.put("ISIM", name.getText().toString());
                contentValues.put("EMAIL", mail.getText().toString());
                contentValues.put("TEL_NO", phone.getText().toString());
                long ID = sqLiteDatabase.update("KAYIT",contentValues,"ID= "+number.getText().toString(),null);
                Toast.makeText(this, ID+"nolu kayıt yapıldı", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
