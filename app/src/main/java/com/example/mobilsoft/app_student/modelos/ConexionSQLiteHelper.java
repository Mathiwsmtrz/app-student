package com.example.mobilsoft.app_student.modelos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {


    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_ITINERARIO);
        db.execSQL(Utilidades.CREAR_TABLA_MATERIAS);
        db.execSQL(Utilidades.CREAR_TABLA_NOTAS);
        db.execSQL(Utilidades.CREAR_TABLA_USUARIOS);
        db.execSQL(Utilidades.CREAR_TABLA_GRABACIONES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_ITINERARIO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_MATERIAS);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_NOTAS);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_GRABACIONES);
        onCreate(db);
    }
}
