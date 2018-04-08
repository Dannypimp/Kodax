package com.example.danny.kodax1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danny.kodax1.Usuarios.Usuario;

public class perfil extends AppCompatActivity {

    TextView nombre, nombreClinica, horario, direccion;


    private Button ubicarme;

    private static final int REQUEST_CALL =1;
    private TextView mButtonNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        ubicarme=(Button)findViewById(R.id.ubicarme);


        ubicarme.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View arg0){
                Intent intent = new Intent(perfil.this, MapsActivity.class);
                startActivity(intent);
            }
        });



        mButtonNumber = (TextView)findViewById(R.id.button_number);
        ImageView imageCall = (ImageView)findViewById(R.id.image_call);

        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        nombre = (TextView) findViewById(R.id.textView31);
        nombreClinica = (TextView) findViewById(R.id.textView2);
        horario = (TextView) findViewById(R.id.textView4);
        direccion = (TextView) findViewById(R.id.textView);

        Bundle obtenerO = getIntent().getExtras();
        Usuario user = null;

        if (obtenerO != null){
            user = (Usuario) obtenerO.getSerializable("usuario");
            nombre.setText(user.getNombre().toString());
            nombreClinica.setText(user.getNombreClinica().toString());
            horario.setText(user.getHorario().toString());
            direccion.setText(user.getDireccion().toString());
            mButtonNumber.setText(user.getTelefono().toString());
        }


    }

    private void makePhoneCall(){
        String number = mButtonNumber.getText().toString();
        if(number.trim().length() > 0 ){

            if(ContextCompat.checkSelfPermission(perfil.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(perfil.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

            }else{
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else{
            Toast.makeText( perfil.this, "enter phone number", Toast.LENGTH_SHORT ).show();

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
