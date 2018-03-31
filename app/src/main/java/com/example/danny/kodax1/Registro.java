package com.example.danny.kodax1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registro extends AppCompatActivity {

    EditText nombre,espe, dir, horar, nClini, telef, corre, contra;
    Button regist;

    DataBase db = new DataBase(this,"BD1",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        nombre = (EditText)findViewById(R.id.edit1);
        espe = (EditText)findViewById(R.id.edit3);
        dir = (EditText)findViewById(R.id.edit2);
        horar = (EditText)findViewById(R.id.edit1sd);
        nClini = (EditText)findViewById(R.id.edit3qsd);
        telef = (EditText)findViewById(R.id.edit21234);
        corre = (EditText)findViewById(R.id.edit12);
        contra = (EditText)findViewById(R.id.edit13);
        regist = (Button)findViewById(R.id.boton);

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.abrir();
                db.insertarregis(String.valueOf(nombre.getText()),String.valueOf(espe.getText()),String.valueOf(dir.getText()),
                        String.valueOf(horar.getText()),String.valueOf(nClini.getText()),String.valueOf(telef.getText())
                        ,String.valueOf(corre.getText()),String.valueOf(contra.getText()));
                db.cerrar();

                Intent i = new Intent(getApplication(),Login.class);
                startActivity(i);
            }
        });
    }


}
