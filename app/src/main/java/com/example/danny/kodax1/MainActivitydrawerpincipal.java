package com.example.danny.kodax1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.danny.kodax1.Usuarios.Usuario;
import com.example.danny.kodax1.conexion.RegistroPost;

import java.util.ArrayList;

public class MainActivitydrawerpincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView cardio ,pedia,derma,od,otorri,general,gine,orto,psi,oftal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitydrawerpincipal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.inflateMenu(R.menu.menu_registro);// inflar menu segun acceso


        // inflar menu segun acceso
        SharedPreferences preferencias = getSharedPreferences("preferenciaLogin",MODE_PRIVATE);
        String sesion = preferencias.getString("sesion","no");
        if (sesion.equals("no")){


           // navigationView.inflateMenu(R.menu.activity_main_activitydrawerpincipal_drawer);

        }else{
            navigationView.inflateMenu(R.menu.menu_inflado);


            //katling
            //xiomara

        }
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
                i.putExtra("key_area", "1");
                startActivity(i);
            }
        });
        pedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "7");
                startActivity(i);
            }
        });
        derma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "2");
                startActivity(i);
            }
        });
        od.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "5");
                startActivity(i);
            }
        });
        otorri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "6");
                startActivity(i);
            }
        });
        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "4");
                startActivity(i);
            }
        });
        gine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "3");
                startActivity(i);
            }
        });
        orto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "Ortopedía");
                startActivity(i);
            }
        });
        psi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "8");
                startActivity(i);
            }
        });
        oftal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaDoctores.class);
                i.putExtra("key_area", "Oftalmología");
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activitydrawerpincipal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.contacto) {

            Intent intent= new Intent(getApplicationContext(), Registro.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.iniciar_i:
                SharedPreferences preferencias = getSharedPreferences("preferenciaLogin",MODE_PRIVATE);
               String sesion = preferencias.getString("sesion","no");
                if (sesion.equals("no")){
                    Intent intentLogin = new Intent(this,Login1.class);
                    startActivity(intentLogin);
                    break;

                }else{
                    Toast.makeText(this,"Usted ya esta logeado",Toast.LENGTH_SHORT).show();

                }


            case R.id.registros:
                Intent intentRegistro = new Intent(this,Registro.class);
                startActivity(intentRegistro);

                break;

            case R.id.cerrar_sesion:


                SharedPreferences preferencia = getSharedPreferences("preferenciaLogin",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencia.edit();
                editor.putString("sesion","no");
                editor.apply();

                finish();

                break;



            // Handle the camera action
        }
       if (id == R.id.iniciar_i) {

           SharedPreferences preferencias = getSharedPreferences("preferenciaLogin",MODE_PRIVATE);
           String sesion = preferencias.getString("sesion","no");
           if (sesion.equals("no")) {
               Intent intentLogin = new Intent(this, Login1.class);
               startActivity(intentLogin);
           }else{
               Toast.makeText(this,"Usted ya esta logueado",Toast.LENGTH_SHORT).show();
           }

        } else if (id == R.id.registros) {
               Intent intentRegistro = new Intent(this,Registro.class);
               startActivity(intentRegistro);

        } else if (id == R.id.cerrar_sesion) {
           SharedPreferences preferencia = getSharedPreferences("preferenciaLogin",MODE_PRIVATE);
           SharedPreferences.Editor editor = preferencia.edit();
           editor.putString("sesion","no");
           editor.apply();
           Toast.makeText(this,"Deslogueado",Toast.LENGTH_SHORT).show();

        }else if (id == R.id.mod_perfil) {
           SharedPreferences preferencias = getSharedPreferences("preferenciaLogin", MODE_PRIVATE);
           String sesion = preferencias.getString("sesion", "no");
           if (sesion.equals("no")) {
               Toast.makeText(this, "Inicie sesion por favor", Toast.LENGTH_SHORT).show();
           } else {
               try {
                       Intent i = new Intent(getApplicationContext(), PerfilUsuarioVer.class);
                       startActivity(i);
                       finish();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
       }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
