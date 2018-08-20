package com.example.danny.kodax1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistroEditable1 extends AppCompatActivity {

    private View mProgressView;
    private View mLoginFormView;
    private AutoCompleteTextView nom;
    private AutoCompleteTextView nomC;
    private AutoCompleteTextView telefono;
    private AutoCompleteTextView dirre;
    private AutoCompleteTextView hora;
    private AutoCompleteTextView id3;



    RequestQueue request;
    JsonObjectRequest jsonRequest;

     String nombre_clinica, nombre23, horario, telefono1, direccion, id_registro;
    String dae, dar, link, link2;
    public static int id2;
    int id1;

    Button mod, elim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_editable1);

        nom = (AutoCompleteTextView) findViewById(R.id.nm);
        nomC = (AutoCompleteTextView) findViewById(R.id.nmC);
        telefono = (AutoCompleteTextView) findViewById(R.id.tele);
        dirre = (AutoCompleteTextView) findViewById(R.id.dire);
        hora = (AutoCompleteTextView) findViewById(R.id.hora);


        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        elim = (Button) findViewById(R.id.eli);

        request = Volley.newRequestQueue(getApplicationContext());
        mod = (Button) findViewById(R.id.mod);

        link = "https://kodaxpro.000webhostapp.com/BDRemota1/actualizar.php";
        link2 = "https://kodaxpro.000webhostapp.com/BDRemota1/Delete.php";

        SharedPreferences preferencias = getSharedPreferences("preferenciaLogin", MODE_PRIVATE);


        id2 = preferencias.getInt("id_user",0);


        String nom1 = preferencias.getString("nombre", "no");
        String clinic = preferencias.getString("clinicas", "no");
        String hora1 = preferencias.getString("horario", "no");
        String dire = preferencias.getString("direccion", "no");
        String cel = preferencias.getString("telefono", "no");


        id_registro = Integer.toString(id2);
        final String pru1 = Integer.toString(id2);


        Bundle bundle = getIntent().getExtras();
        String nombreClini = bundle.getString("nmC");
        final String nombre = bundle.getString("nm");
        String tele = bundle.getString("tel");
        String direc = bundle.getString("dir");
        String ho = bundle.getString("ho");
        id1 = bundle.getInt("id_key", 0);

        nomC.setText(clinic);
        nom.setText(nom1);
        telefono.setText(cel);
        dirre.setText(dire);
        hora.setText(hora1);





        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                nombre_clinica = nomC.getText().toString();
                nombre23 = nom.getText().toString();
                horario = hora.getText().toString();
                telefono1 = telefono.getText().toString();
                direccion = dirre.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest StringRe = new StringRequest(Request.Method.POST, link,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), "se ha Actualizado exitosamente ", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), MainActivitydrawerpincipal.class);
                                startActivity(i);
                                finish();

                            }
                        }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                })
                {
                    @Override
                    protected Map<String,String> getParams(){


                        Map<String,String> para = new HashMap<String, String>();
                        para.put("nameCli", nombre_clinica);
                        para.put("name", nombre23);
                        para.put("hora", horario);
                        para.put("tele", telefono1);
                        para.put("dire", direccion);
                        para.put("id", id_registro);



                        return para;
                    }
                };

                int socketTimeout = 990000;//tiempo de espera
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                StringRe.setRetryPolicy(policy);
                queue.add(StringRe);

            }
        });
        //https://mega.nz/#F!sKA1GABb
        //https://mega.nz/#F!Vf5gDIrI


        elim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(RegistroEditable1.this);
                builder.setTitle("Eliminar cuenta");
                builder.setMessage("Esta seguro que quiere eliminar su cuenta");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest StringRe = new StringRequest(Request.Method.POST, link2,
                                new com.android.volley.Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getApplicationContext(), "se elimino exitosamente ", Toast.LENGTH_LONG).show();

                                        Intent i = new Intent(getApplicationContext(), MainActivitydrawerpincipal.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }, new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }

                        })
                        {
                            @Override
                            protected Map<String,String> getParams(){


                                Map<String,String> para = new HashMap<String, String>();
                                para.put("id", id_registro);



                                return para;
                            }
                        };

                        int socketTimeout = 990000;//tiempo de espera
                        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                        StringRe.setRetryPolicy(policy);
                        queue.add(StringRe);
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                Dialog dialog = builder.create();
                dialog.show();



            }
        });
    }



}
