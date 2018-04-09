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
import android.widget.TextView;

import com.example.danny.kodax1.Usuarios.Usuario;
import java.util.ArrayList;

public class ListaDoctores extends AppCompatActivity {

    ArrayList<String> extra;
    ArrayList<Usuario> usuarios;
    ListView lista;
    AdapterList ada;

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

        ada = new AdapterList(getApplicationContext(), ver(area));
        lista.setAdapter(ada);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario user = usuarios.get(position);
                Intent i = new Intent(getApplicationContext(),perfil.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", user);
                i.putExtras(bundle);

                startActivity(i);
            }
        });
    }

    private ArrayList<Usuario> ver(String especialidad) {

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
            u.setTelefono(c.getString(8));
            u.setHorario(c.getString(7));
            u.setCorreo(c.getString(3));
            u.setContrasena(c.getString(4));
            usuarios.add(u);
        }
        return usuarios;
    }
}
