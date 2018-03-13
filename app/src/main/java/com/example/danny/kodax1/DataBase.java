package com.example.danny.kodax1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Danny on 02/03/2018.
 */

public class DataBase extends SQLiteOpenHelper {


    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query="create table registros(ID INTEGER PRIMARY KEY AUTOINCREMENT, Nombre text, Correo text ,Contraseña text );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void abrir(){
        this.getWritableDatabase();
    }


    public void cerrar(){
        this.close();
    }

    public void insertarregis(String nom, String correo, String pass) {
        ContentValues valores = new ContentValues();
        valores.put("Nombre", nom);
        valores.put("Correo", correo);
        valores.put("Contraseña", pass);
        this.getWritableDatabase().insert("registros", null, valores);
    }

    public Cursor consultLogin(String usu, String pass)throws SQLException {

        Cursor mcursor = null;
        mcursor=this.getReadableDatabase().query("registros",new String[]{"ID","Nombre","Correo","Contraseña"},
                "Correo like '"+usu+"' and Contraseña like '"+pass+"' ",null,null,null,null);
        return mcursor;
    }
}
