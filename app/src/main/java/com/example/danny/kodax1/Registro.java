package com.example.danny.kodax1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Registro extends AppCompatActivity {

    Spinner esp;
    EditText nombreClinica, nombre, corre, contra,  dir,hor, tel;
    Button regist;

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
        regist = (Button)findViewById(R.id.boton);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource (this, R.array.esp, android.R.layout.simple_spinner_item);
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


                }
                else  if(validarSintaxisCorreo(corre.getText().toString())){

                    nClinica = nombreClinica.getText().toString();
                    nom = nombre.getText().toString();
                    correo = corre.getText().toString();
                    cont = contra.getText().toString();
                    direc = dir.getText().toString();
                    hora = hor.getText().toString();
                    tele = tel.getText().toString();
                    espe = esp.getSelectedItem().toString();
                    Toast.makeText(getApplicationContext(), "Correo invalido", Toast.LENGTH_SHORT).show();



                }

                else{
                    //Toast.makeText(getApplicationContext(), "Correo invalido", Toast.LENGTH_SHORT).show();

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

            Intent i = new Intent(getApplication(),Principal.class);
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

}