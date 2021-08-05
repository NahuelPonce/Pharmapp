package com.example.pharmapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "carrito.sqlite";
    public static final String TABLE_CARRITO = "t_carrito";

    public DbHelper(Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CARRITO + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "medicamentoId INT NOT NULL," +
                "nombre TEXT," +
                "precio FLOAT," +
                "imagen INT," +
                "cantidad INT," +
                "total FLOAT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
