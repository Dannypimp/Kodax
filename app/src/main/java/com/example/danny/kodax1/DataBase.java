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

        String query="create table registros(ID INTEGER PRIMARY KEY AUTOINCREMENT,NombreClinica text, Nombre text, Correo text ,Contraseña text," +
                "Especialida text, Direccion text,HorarioAtencion text, Telefono text , Latitud INTEGER, Longitud INTEGER);";
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

    public void insertarregis(String nomCli,String nom, String correo, String pass, String espe, String dire,String hora, String tele, Double latitud, Double longitud) {
        ContentValues valores = new ContentValues();

        valores.put("Longitud", longitud);
        valores.put("Telefono", tele);
        valores.put("Contraseña", pass);
        valores.put("NombreClinica", nomCli);
        valores.put("Correo", correo);
        valores.put("Nombre", nom);
        valores.put("Latitud", latitud);
        valores.put("Direccion", dire);
        valores.put("Telefono", tele);
        valores.put("HorarioAtencion", hora);
        valores.put("Especialida", espe);


        this.getWritableDatabase().insert("registros", null, valores);
    }

    public Cursor consultLogin(String usu, String pass)throws SQLException {

        Cursor mcursor = null;
        mcursor=this.getReadableDatabase().query("registros",new String[]{"ID","NombreClinica","Nombre","Correo","Contraseña, Especialida, Direccion, Telefono"},
                "Correo like '"+usu+"' and Contraseña like '"+pass+"' ",null,null,null,null,null);
        return mcursor;
    }
}
