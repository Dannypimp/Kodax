package com.example.danny.kodax1;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danny.kodax1.Usuarios.Usuario;

public class PerfilUsuarioVer extends AppCompatActivity {

    TextView nombreClinica,nombre, correo, horario_atencion, direccion, telefono, nom,cor;
    private static final int REQUEST_CALL =1;
    private static int id;
    private Button buttonMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario_ver);

        ImageView imageCall = (ImageView)findViewById(R.id.image_call);
        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        nombreClinica= (TextView) findViewById(R.id.tvNomCliPv);
        nombre= (TextView) findViewById(R.id.tvNomDoc);
        correo = (TextView) findViewById(R.id.textView3PV);

        horario_atencion = (TextView) findViewById(R.id.horarioPV);

        direccion = (TextView )findViewById(R.id.textViewPV);
        telefono = (TextView) findViewById(R.id.tvTelefonoPV);

        buttonMapa = (Button) findViewById(R.id.ubicarmePV);

       SharedPreferences preferencias = getSharedPreferences("preferenciaLogin", MODE_PRIVATE);


        final Integer id = preferencias.getInt("id_user",0);

        String nom = preferencias.getString("nombre", "no");
        String clinic = preferencias.getString("clinicas", "no");
        String hora = preferencias.getString("horario", "no");
        String dire = preferencias.getString("direccion", "no");
        String cel = preferencias.getString("telefono", "no");
        nombre.setText(nom);
        nombreClinica.setText(clinic);
        direccion.setText(dire);
        telefono.setText(cel);
        horario_atencion.setText(hora);


        buttonMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("id_key", id);
                startActivity(intent);
            }
        });
    }

    private void makePhoneCall(){
        String number = telefono.getText().toString();
        if(number.trim().length() > 0 ){

            if(ContextCompat.checkSelfPermission(PerfilUsuarioVer.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(PerfilUsuarioVer.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

            }else{
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else{
            Toast.makeText( PerfilUsuarioVer.this, "Enter en el número de celular", Toast.LENGTH_SHORT ).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else{
                Toast.makeText(this,"Permiso negado", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.editar){
            Intent i = new Intent(getApplicationContext(),RegistroEditable.class);
            startActivity(i);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}


