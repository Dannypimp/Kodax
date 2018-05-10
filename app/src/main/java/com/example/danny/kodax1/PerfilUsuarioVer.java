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
    private static double lati , longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        

        ImageView imageCall = (ImageView)findViewById(R.id.image_call);
        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        nombreClinica= (TextView) findViewById(R.id.tvNomCliPv);
        nombre= (TextView) findViewById(R.id.tvNomDoc);
        correo = (TextView) findViewById(R.id.textView3);

        horario_atencion = (TextView) findViewById(R.id.horarioPV);

        direccion = (TextView )findViewById(R.id.textView3PV);
        telefono = (TextView) findViewById(R.id.tvTelefono);
        buttonMapa = (Button) findViewById(R.id.ubicarme);

        Bundle bundle = getIntent().getExtras();
        Usuario usuario = null;

        if(bundle != null){
            usuario = (Usuario) bundle.getSerializable("usuario");
            nombre.setText(usuario.getNombre());
            nombreClinica.setText(usuario.getNombreClinica());
            direccion.setText(usuario.getDireccion());
            telefono.setText(usuario.getTelefono());
            horario_atencion.setText(usuario.getHorario());
            id = usuario.getId();
            lati = usuario.getLatitud();
            longi = usuario.getLongitud();
        }

        buttonMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("id_key", id);
                intent.putExtra("lat_key", lati);
                intent.putExtra("lon_key", longi);
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
            Toast.makeText( PerfilUsuarioVer.this, "enter phone number", Toast.LENGTH_SHORT ).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else{
                Toast.makeText(this,"permiso negado", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
