package com.example.danny.kodax1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Odontologia extends AppCompatActivity {

    ListView lista;

    DataBase db = new DataBase(this,"BD1",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odontologia);

        lista = (ListView)findViewById(R.id.ListOdo);

        ver();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplication(),perfil.class);
                startActivity(i);
            }
        });

    }



    private void ver() {

        DataBase dataBase = new DataBase(this,"BD1",null,1);
        SQLiteDatabase db = dataBase.getReadableDatabase();
        if(db!=null){
            Cursor c = db.rawQuery("SELECT * from registros",null);
            int cantidad = c.getCount();
            int i=0;
            String[] arreglo= new String[cantidad];
            if (c.moveToFirst()){
                do {

                    String linea= c.getString(1);

                    arreglo [i] = linea;
                    i++;



                }while (c.moveToNext());
            }

            ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arreglo);
            lista.setAdapter(adapter);



        }





    }

}
