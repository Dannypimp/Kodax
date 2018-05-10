package com.example.danny.kodax1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.danny.kodax1.Usuarios.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaDoctores extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener, Response.Listener<JSONObject>, Response.ErrorListener {

    ArrayList<String> extra;
    ArrayList<Usuario> usuarios;
    ListView lista;
    AdapterList ada;

    RequestQueue request;
    JsonObjectRequest jsonRequest;

    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_doctores);

        usuarios = new ArrayList<Usuario>();
        Bundle data = getIntent().getExtras();
        String area = data.getString("key_area", "hola");

        request = Volley.newRequestQueue(getApplicationContext());
        db = new DataBase(this, "BD1", null, 1);

        lista = (ListView) findViewById(R.id.ListOdo);

        //ver(area);
        verBDR(area);

        //ada = new AdapterList(getApplicationContext(), ver(area));
        //lista.setAdapter(ada);


        /*lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario user = usuarios.get(position);
                Intent i = new Intent(getApplicationContext(), perfil.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", user);
                i.putExtras(bundle);

                startActivity(i);

            }
        });*/
    }

    private void verBDR(String especialidad) {

        String url = "https://kodaxpro.000webhostapp.com/BDRemota1/prueba.php?especialidad="+especialidad;
        jsonRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonRequest);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_buscar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_buscar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(this);


        MenuItemCompat.setOnActionExpandListener(searchItem, this);


        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_buscar:
                Log.i("ActionBar", "Buscar");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(getApplication(), "Buscando...", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        ada.getFilter().filter(newText);
        return true;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {

        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {

        return true;
    }

    /*private ArrayList<Usuario> ver(String especialidad) {

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
    }*/

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(), "no hay Doctores disponibles para esta especialidad", Toast.LENGTH_SHORT).show();
        System.out.println();
        Log.i("ERROR",error.toString() );

    }

    @Override
    public void onResponse(JSONObject response) {

        Usuario usuario = null;

        JSONArray json = response.optJSONArray("registros");


        try {
            if(json.length()==0){
                Toast.makeText(getApplicationContext(), "no hay registros disponibles", Toast.LENGTH_SHORT).show();
            }else {
                for (int i = 0; i < json.length(); i++) {
                    usuario = new Usuario();
                    JSONObject jsonOb = null;
                    jsonOb = json.getJSONObject(i);

                    usuario.setId(jsonOb.optInt("id_registro"));
                    usuario.setNombreClinica(jsonOb.optString("nombre_clinica"));
                    usuario.setNombre(jsonOb.optString("nombre"));
                    usuario.setTelefono(jsonOb.optString("telefono"));
                    usuario.setDireccion(jsonOb.optString("direccion"));
                    usuario.setHorario(jsonOb.optString("horario"));
                    usuario.setLatitud(jsonOb.optDouble("longitud"));
                    usuario.setLongitud(jsonOb.optDouble("latitud"));

                    usuarios.add(usuario);


                }
            }
            ada = new AdapterList(getApplicationContext(), usuarios);
            lista.setAdapter(ada);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Usuario user = usuarios.get(position);
                    Intent i = new Intent(getApplicationContext(), perfil.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("usuario", user);
                    i.putExtras(bundle);

                    startActivity(i);

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No se hay conexion con el servidos" +response, Toast.LENGTH_SHORT).show();
        }

    }
}
