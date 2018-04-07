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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class perfil extends AppCompatActivity {
    EditText esp,dire,tel;

    TextView nombreClinica,nombre, correo, especialidad, direccion, telefono, nom,cor;
    private static final int REQUEST_CALL =1;
    private TextView mButtonNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        nombreClinica= (TextView)findViewById(R.id.textView1);
        nombre= (TextView)findViewById(R.id.textView2);
        correo = (TextView)findViewById(R.id.textView3);
        especialidad = (TextView)findViewById(R.id.textView4);
        direccion = (TextView)findViewById(R.id.textView);
        telefono = (TextView)findViewById(R.id.button_number);


        mButtonNumber = (TextView) findViewById(R.id.button_number);
        ImageView imageCall = (ImageView)findViewById(R.id.image_call);

        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        Bundle bundle = getIntent().getExtras();

        String ka, ka1, ka2, ka3, ka4;
        ka = bundle.getString("da").toString();
        ka1 = bundle.getString("da1").toString();
        ka2 = bundle.getString("da2").toString();
        ka3 = bundle.getString("da3").toString();
        ka4 = bundle.getString("da4").toString();

        nombre.setText(ka);
        correo.setText(ka1);
        especialidad.setText(ka2);
        direccion.setText(ka3);
        telefono.setText(ka4);

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
