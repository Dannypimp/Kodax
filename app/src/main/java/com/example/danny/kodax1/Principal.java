package com.example.danny.kodax1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;



public class Principal extends AppCompatActivity {

ImageView cardio ,pedia,derma,od,otorri,general,gine,orto,psi,oftal ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cardio = (ImageView)findViewById(R.id.card);
        pedia= (ImageView)findViewById(R.id.pedi);
        derma= (ImageView)findViewById(R.id.derma);
        od = (ImageView)findViewById(R.id.od);
        otorri = (ImageView)findViewById(R.id.otorri);
        general = (ImageView)findViewById(R.id.general);
        gine = (ImageView)findViewById(R.id.gine);
        orto = (ImageView)findViewById(R.id.orto);
        psi= (ImageView)findViewById(R.id.psi);
        oftal = (ImageView)findViewById(R.id.oftal);


        cardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "Cardiologia");
                startActivity(i);
            }
        });
        pedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "Pediatria");
                startActivity(i);
            }
        });
        derma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "Dermatologia");
                startActivity(i);
            }
        });
        od.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "Odontologia");
                startActivity(i);
            }
        });
        otorri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "Otorinolaringologia");
                startActivity(i);
            }
        });
        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "Medicina_General");
                startActivity(i);
            }
        });
        gine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "Ginecologia");
                startActivity(i);
            }
        });
        orto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "Ortopedia");
                startActivity(i);
            }
        });
        psi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "Psicologia");
                startActivity(i);
            }
        });
        oftal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "Oftalmologia");
                startActivity(i);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

            if (id == R.id.contacto){
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                return true;
            }


        return super.onOptionsItemSelected(item);
    }
}
