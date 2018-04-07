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

import com.example.danny.kodax1.Usuarios.Usuario;

import java.util.ArrayList;

public class ListaDoctores extends AppCompatActivity {


    ArrayList<String> extra;
    ArrayList<Usuario> usuarios;
    ListView lista;
    String condi, corre, espe, dire, tele;

    DataBase db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_doctores);

        Bundle data = getIntent().getExtras();
        String area = data.getString("key_area","hola");


        db = new DataBase(this,"BD1",null,1);

        lista = (ListView)findViewById(R.id.ListOdo);



        ver(area);

        ArrayAdapter ada = new ArrayAdapter(this, android.R.layout.simple_list_item_1,extra);
        lista.setAdapter(ada);







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

        SQLiteDatabase db1 = db.getReadableDatabase();

        Usuario u = null;

        usuarios =  new ArrayList<Usuario>();

        Cursor c = db1.rawQuery("SELECT * FROM registros WHERE Especialida = '"+especialidad+"'",null   );

        while (c.moveToNext()){
            u = new Usuario();

            u.setId(c.getInt(0));
            u.setNombre(c.getString(2));
            u.setEspecialidad(c.getString(5));
            u.setDireccion(c.getString(6));
            u.setNombreClinica(c.getString(1));
            u.setTelefono(c.getString(7));
            u.setCorreo(c.getString(3));
            u.setContrasena(c.getString(4));

            usuarios.add(u);
        }

        obtener();

    }

    private void obtener() {
        extra= new ArrayList<String>();

        for (int i=0; i<usuarios.size(); i++){

            extra.add(usuarios.get(i).getNombreClinica());
        }

    }
}
