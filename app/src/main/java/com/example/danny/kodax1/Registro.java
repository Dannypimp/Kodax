package com.example.danny.kodax1;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Registro extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    Spinner esp;
    EditText nombreClinica, nombre, corre, contra,  dir,hor, tel;
    CardView regist;

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjet;
    String nClinica, nom, correo, cont, direc,hora, tele, espe;
    DataBase db = new DataBase(this,"BD1",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        sharedPreferences.getAll().clear();

        nombreClinica = (EditText)findViewById(R.id.edit0);
        nombre = (EditText)findViewById(R.id.edit1);
        corre = (EditText)findViewById(R.id.edit2);
        contra = (EditText)findViewById(R.id.edit3);
        esp = (Spinner)findViewById(R.id.sp_especial);
        dir = (EditText)findViewById(R.id.edit5);
        hor = (EditText)findViewById(R.id.edit7);
        tel = (EditText)findViewById(R.id.edit6);
        regist = (CardView) findViewById(R.id.boton);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource (this, R.array.esp, R.layout.spinner_item_espe);
        esp.setAdapter(adapter);

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder builder = new AlertDialog.Builder(Registro.this);
                builder.setTitle("Registro");
                if (nombreClinica.getText().toString().isEmpty()) {


                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                    nombreClinica.requestFocus();

                } else if (nombre.getText().toString().isEmpty()) {


                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                    nombre.requestFocus();
                } else if (corre.getText().toString().isEmpty()) {


                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                    corre.requestFocus();


                } else if (contra.getText().toString().isEmpty()) {


                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                    contra.requestFocus();


                } else if (dir.getText().toString().isEmpty()) {


                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                    dir.requestFocus();


                } else if (tel.getText().toString().isEmpty()) {


                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                    tel.requestFocus();


                } else if (esp.getSelectedItem().toString().isEmpty()) {


                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                    esp.requestFocus();


                } else if(validarSintaxisCorreo(corre.getText().toString())){

                    nClinica = nombreClinica.getText().toString();
                    nom = nombre.getText().toString();
                    correo = corre.getText().toString();
                    cont = contra.getText().toString();
                    direc = dir.getText().toString();
                    hora = hor.getText().toString();
                    tele = tel.getText().toString();
                    espe = esp.getSelectedItem().toString();

                    builder.setMessage("Desea agregar su abicacion geografíca para que sus clientes encuentren mas facil su clinica?");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), MapsActivity2.class);
                            startActivityForResult(intent, 1);
                        }
                    });

                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            regsitro(0, 0);
                        }
                    });

                    Dialog dialog = builder.create();
                    dialog.show();

                } else {
                    Toast.makeText(getApplicationContext(), "Correo invalido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        double latitud = data.getDoubleExtra("latitud_key", 0.0);
        double longitud = data.getDoubleExtra("longitud_key", 0.0);
        regsitro(longitud, latitud);
        Toast.makeText(Registro.this,  ""+ latitud + " , "+ longitud,  Toast.LENGTH_SHORT).show();
       // Toast.makeText(getApplicationContext(), "Registro exitoso con ubicación", Toast.LENGTH_SHORT).show();
        finish();

    }

    public void regsitro(double latitud, double longitud ){

            if(longitud == 0 && latitud == 0){
                Toast.makeText(getApplicationContext(), "Registro exitoso sin ubicación", Toast.LENGTH_SHORT).show();
            }

            db.abrir();
            db.insertarregis(nClinica, nom, correo, cont, espe, direc,hora, tele, longitud, latitud);
            db.cerrar();

            /*String url = "https://kodaxpro.000webhostapp.com/BDRemota1/JSONregistros.php?nombre_clinica=\"+nClinica+\"&nombre=\"+nom+\"&correo=\"+correo+\"&contrasena=\"+cont+\"&especialidad=\"+espe+\"&direccion=\"+direc+\"&horario=\"+hora+\"&telefono=\"+tele+\"&latitud=\"+latitud+\"&longitud="+longitud;

            url = url.replace(" ", "%20");
        jsonObjet = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjet);*/

            Intent i = new Intent(getApplication(),MainActivitydrawerpincipal.class);
            startActivity(i);
            finish();
    }


    public static boolean validarSintaxisCorreo(String campoCorreo){

        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(campoCorreo);

        if (mather.find() == true) {
            return true;
        } else {
//           JOptionPane.showMessageDialog(this, "Email ingresado es inválido.");
            return false;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(), "no se ha registrado", Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {

        Toast.makeText(getApplicationContext(), "se ha registrado", Toast.LENGTH_SHORT).show();

    }
}