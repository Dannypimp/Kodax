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

public class ListaDoctores extends AppCompatActivity {

    ListView lista;
    String condi, corre, espe, dire, tele;

    DataBase db = new DataBase(this,"BD1",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_doctores);

        Bundle data = getIntent().getExtras();
        String area = data.getString("key_area","hola");

        lista = (ListView)findViewById(R.id.ListOdo);

        ver(area);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplication(),perfil.class);
                i.putExtra("da", condi);
                i.putExtra("da1", corre);
                i.putExtra("da2", espe);
                i.putExtra("da3", dire);
                i.putExtra("da4", tele);

                startActivity(i);
            }
        });

    }



    private void ver(String especialidad) {

        DataBase dataBase = new DataBase(this,"BD1",null,1);
        SQLiteDatabase db = dataBase.getReadableDatabase();
        if(db!=null){
            Cursor c = db.rawQuery("SELECT * FROM registros WHERE Especialida = '"+especialidad+"';",null);
            int cantidad = c.getCount();
            int i=0;
            String[] arreglo= new String[cantidad];
            if (c.moveToFirst()){
                do {

                    condi = c.getString(1);
                    corre = c.getString(2);
                    espe = c.getString(4);
                    dire = c.getString(5);
                    tele = c.getString(6);

                    arreglo [i] = condi;
                    i++;



                }while (c.moveToNext());
            }else{
                Toast.makeText(getApplicationContext(), "No hay registros", Toast.LENGTH_SHORT).show();
            }

            ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arreglo);
            lista.setAdapter(adapter);



        }





    }

}
