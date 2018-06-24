package com.example.danny.kodax1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Login1 extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
//--------------------------------------- xiomara ------------------------------------------------------

    ArrayList<Usuario> usuarioArrayList;
    TextView usu, pass;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Button iniSecion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        usuarioArrayList = new ArrayList<Usuario>();
        usu = (TextView) findViewById(R.id.editText);
        pass = (TextView) findViewById(R.id.editText2);

        iniSecion = (Button) findViewById(R.id.iniSe);

        request = Volley.newRequestQueue(getApplicationContext());

        iniSecion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usu.getText().toString().isEmpty()  ){

                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                    usu.requestFocus();
                }else if (pass.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                    pass.requestFocus();
                }else {

                    iniciarSesion();
                }
            }
        });
    }
/*                                    DANNIEL*/
    public void iniciarSesion(){
        String url = "https://kodaxpro.000webhostapp.com/BDRemota1/validacion.php?correo="+usu.getText().toString()+"&contrasena="+pass.getText().toString();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);

    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(), "correo o contraseña son invalidas", Toast.LENGTH_SHORT).show();

    }
//----------------------xiomara
    @Override
    public void onResponse(JSONObject response) {

        Toast.makeText(getApplicationContext(), "se ha registrado correctamente", Toast.LENGTH_SHORT).show();

        Usuario usuario = null;

        JSONArray jsonA = response.optJSONArray("registros");

        try {
            for (int i = 0; i<jsonA.length(); i++){
                usuario = new Usuario();
                JSONObject jsonO = null;
                jsonO = jsonA.getJSONObject(i);

                usuario.setId(jsonO.getInt("id_registro"));
                usuario.setNombreClinica(jsonO.optString("nombre_clinica"));
                usuario.setNombre(jsonO.optString("nombre"));
                usuario.setCorreo(jsonO.optString("correo"));
                usuario.setContrasena(jsonO.optString("contrasena"));
                usuario.setEspecialidad(jsonO.optString("especialidad"));
                usuario.setDireccion(jsonO.optString("direccion"));
                usuario.setHorario(jsonO.optString("horario"));
                usuario.setTelefono(jsonO.optString("telefono"));
                usuario.setLatitud(jsonO.optDouble("latitud"));
                usuario.setLongitud(jsonO.optDouble("longitud"));

                usuarioArrayList.add(usuario);
            }


            SharedPreferences preferencias = getSharedPreferences("preferenciaLogin",MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putString("sesion","si");
            int id = usuario.getId();
            editor.putInt("id_user", id);
            editor.putString("clinicas",usuario.getNombreClinica());
            editor.putString("nombre",usuario.getNombre());
            editor.putString("horario",usuario.getHorario());
            editor.putString("direccion",usuario.getDireccion());
            editor.putString("telefono",usuario.getTelefono());
            editor.apply();

            Intent i = new Intent(getApplicationContext(),PerfilUsuarioVer.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuarioArrayList.get(0));
            i.putExtras(bundle);
            startActivity(i);
            finish();

            /*Usuario user = usuarioArrayList.get(0);

            Intent i = new Intent(getApplicationContext(), perfil.class);
            startActivity(i);*/
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
