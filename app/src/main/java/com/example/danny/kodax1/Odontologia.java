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
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.danny.kodax1.Usuarios.Usuario;

import java.util.ArrayList;


public class Odontologia extends AppCompatActivity {

    ListView lista;
    ArrayList<String> algo;
    ArrayList<Usuario> usuarios;

    DataBase db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odontologia);

        db = new DataBase(this,"BD1",null,1);

        lista = (ListView)findViewById(R.id.ListOdo);


        cosultadb();

        ArrayAdapter ada = new ArrayAdapter(this, android.R.layout.simple_list_item_1,algo);
        lista.setAdapter(ada);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Usuario user = usuarios.get(position);

                Intent i = new Intent(Odontologia.this, perfil.class);

                Bundle bun = new Bundle();
                bun.putSerializable("usuario", user);

                i.putExtras(bun);
                startActivity(i);

            }
        });

    }

    private void cosultadb() {
        SQLiteDatabase db1 = db.getReadableDatabase();

        Usuario u = null;

        usuarios =  new ArrayList<Usuario>();

        Cursor c = db1.rawQuery("SELECT * FROM registros ", null);

        while (c.moveToNext()){
            u = new Usuario();

            u.setId(c.getInt(0));
            u.setNombre(c.getString(1));
            u.setEspecialidad(c.getString(2));
            u.setDireccion(c.getString(3));
            u.setHorario(c.getString(4));
            u.setNombreClinica(c.getString(5));
            u.setTelefono(c.getString(6));
            u.setCorreo(c.getString(7));
            u.setContrasena(c.getString(8));

            usuarios.add(u);
        }

        Obtener();
    }

    private void Obtener() {
        algo= new ArrayList<String>();

        for (int i=0; i<usuarios.size(); i++){

            algo.add(usuarios.get(i).getNombre());
        }

    }


    private void ver() {


    }

}
