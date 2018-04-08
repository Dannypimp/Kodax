package com.example.danny.kodax1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;



public class Registro extends AppCompatActivity {

    Spinner esp;
    EditText nombreClinica, nombre, corre, contra,  dir, tel;
    Button regist;

    String nClinica, nom, correo, cont, direc, tele, espe;
    DataBase db = new DataBase(this,"BD1",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        nombreClinica = (EditText)findViewById(R.id.edit0);
        nombre = (EditText)findViewById(R.id.edit1);
        corre = (EditText)findViewById(R.id.edit2);
        contra = (EditText)findViewById(R.id.edit3);
        esp = (Spinner)findViewById(R.id.sp_especial);
        dir = (EditText)findViewById(R.id.edit5);
        tel = (EditText)findViewById(R.id.edit6);
        regist = (Button)findViewById(R.id.boton);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource (this, R.array.esp, android.R.layout.simple_spinner_item);
        esp.setAdapter(adapter);

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nClinica = nombreClinica.getText().toString();
                nom = nombre.getText().toString();
                correo = corre.getText().toString();
                cont = contra.getText().toString();
                direc = dir.getText().toString();
                tele = tel.getText().toString();
                espe = esp.getSelectedItem().toString();

                db.abrir();
                db.insertarregis(nClinica, nom, correo, cont, espe, direc, tele, 5374.653, 5347.6523);
                db.cerrar();

                Intent i = new Intent(getApplication(),Principal.class);
                startActivity(i);
            }
        });
    }




}
