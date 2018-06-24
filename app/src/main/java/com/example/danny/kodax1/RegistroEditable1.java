package com.example.danny.kodax1;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegistroEditable1 extends AppCompatActivity {


    RequestQueue request;
    JsonObjectRequest jsonRequest;

    String dae, dar;
    public static int id2;
    int id1;
    EditText nom, nomC, telefono, dirre, hora, id3;
    Button mod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_editable1);


        //ANTONY
        // -------------------------

        nom = (EditText) findViewById(R.id.nm);
        nomC = (EditText) findViewById(R.id.nmC);
        telefono = (EditText) findViewById(R.id.tele);
        dirre = (EditText) findViewById(R.id.dire);
        hora = (EditText) findViewById(R.id.hora);
        id3 = (EditText) findViewById(R.id.id);

        request = Volley.newRequestQueue(getApplicationContext());
        mod = (Button) findViewById(R.id.mod);

        dae = Integer.toString(id2);
        dar = Integer.toString(id1);


        SharedPreferences preferencias = getSharedPreferences("preferenciaLogin", MODE_PRIVATE);


        id2 = preferencias.getInt("id_user",0);


        String nom1 = preferencias.getString("nombre", "no");
        String clinic = preferencias.getString("clinicas", "no");
        String hora1 = preferencias.getString("horario", "no");
        String dire = preferencias.getString("direccion", "no");
        String cel = preferencias.getString("telefono", "no");





        Bundle bundle = getIntent().getExtras();
        String nombreClini = bundle.getString("nmC");
        String nombre = bundle.getString("nm");
        String tele = bundle.getString("tel");
        String direc = bundle.getString("dir");
        String ho = bundle.getString("ho");
        id1 = bundle.getInt("id_key", 0);

        nomC.setText(clinic);
        nom.setText(nom1);
        telefono.setText(cel);
        dirre.setText(dire);
        hora.setText(hora1);
        id3.setText(dar);

        //https://mega.nz/#F!sKA1GABb
        //https://mega.nz/#F!Vf5gDIrI


    }



}
