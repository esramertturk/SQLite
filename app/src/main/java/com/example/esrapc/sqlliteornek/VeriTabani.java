package com.example.esrapc.sqlliteornek;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class VeriTabani  extends SQLiteOpenHelper {
    //ilk olarak extends SQLiteOpenHelper yazılır, ardından alt enter ile hatalar seçilir aşağıdaki düzenek oto. geliyor
    public VeriTabani(Context context) {
        super(context, "adres_defteri", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE KAYIT (ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "ISIM text,TEL_NO text ,EMAIL text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
