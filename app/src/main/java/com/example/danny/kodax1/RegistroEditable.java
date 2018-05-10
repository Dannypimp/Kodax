package com.example.danny.kodax1;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.READ_CONTACTS;

public class RegistroEditable extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {

    private View mProgressView;
    private View mLoginFormView;
    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mEmailView1;
    private AutoCompleteTextView mEmailView2;
    private AutoCompleteTextView mEmailView3;
    private AutoCompleteTextView mEmailView4;
    private AutoCompleteTextView mEmailView5;
    private AutoCompleteTextView mEmailView6;

    Spinner esp;
    EditText nombreClinica, nombre, corre, contra,  dir,hor, tel;
    CardView guardar;


    String nClinica, nom, correo, cont, direc,hora, tele, espe;
    DataBase db = new DataBase(this,"BD1",null,1);


    private static final int REQUEST_READ_CONTACTS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_editable);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.edit0);
        mEmailView1 = (AutoCompleteTextView) findViewById(R.id.edit1);
        mEmailView2 = (AutoCompleteTextView) findViewById(R.id.edit2);
        mEmailView3 = (AutoCompleteTextView) findViewById(R.id.edit3);
        mEmailView4 = (AutoCompleteTextView) findViewById(R.id.edit5);
        mEmailView5 = (AutoCompleteTextView) findViewById(R.id.edit7);
        mEmailView6 = (AutoCompleteTextView) findViewById(R.id.edit6);
        populateAutoComplete();

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        sharedPreferences.getAll().clear();

        SharedPreferences preferencias = getSharedPreferences("preferenciaLogin", MODE_PRIVATE);


        final Integer id = preferencias.getInt("id_user",0);

        final String[] nom = {preferencias.getString("nombre", "no")};
        String clinic = preferencias.getString("clinicas", "no");
        final String[] hora = {preferencias.getString("horario", "no")};
        String dire = preferencias.getString("direccion", "no");
        String cel = preferencias.getString("telefono", "no");


        nombreClinica = (EditText) findViewById(R.id.edit0);
        nombre = (EditText) findViewById(R.id.edit1);

        esp = (Spinner) findViewById(R.id.sp_especial);
        dir = (EditText) findViewById(R.id.edit5);
        hor = (EditText) findViewById(R.id.edit7);
        tel = (EditText) findViewById(R.id.edit6);
        guardar = (CardView) findViewById(R.id.botonGuardar);

        nombre.setText(nom[0]);
        nombreClinica.setText(clinic);
        dir.setText(dire);
        tel.setText(cel);
        hor.setText(hora[0]);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.esp, android.R.layout.simple_spinner_item);
        esp.setAdapter(adapter);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(RegistroEditable.this);
                builder.setTitle("Registro");
                if (nombreClinica.getText().toString().isEmpty()) {


                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                    nombreClinica.requestFocus();

                } else if (nombre.getText().toString().isEmpty()) {


                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                    nombre.requestFocus();

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

                nClinica = nombreClinica.getText().toString();
                nom[0] = nombre.getText().toString();

                direc = dir.getText().toString();
                hora[0] = hor.getText().toString();
                tele = tel.getText().toString();
                espe = esp.getSelectedItem().toString();

                builder.setMessage("Desea agregar su ubicación geográfica para que sus clientes encuentren más fácil su clinica?");
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
                        actualizarUsuario(0.0,0.0);
                    }
                });

                Dialog dialog = builder.create();
                dialog.show();


            }

        });


    }
    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }
    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        double latitud = data.getDoubleExtra("latitud_key", 0.0);
        double longitud = data.getDoubleExtra("longitud_key", 0.0);
        actualizarUsuario(longitud, latitud);
        Toast.makeText(RegistroEditable.this,  ""+ latitud + " , "+ longitud,  Toast.LENGTH_SHORT).show();
        // Toast.makeText(getApplicationContext(), "Registro exitoso con ubicación", Toast.LENGTH_SHORT).show();
        finish();

    }

    public void actualiarUsuario(double latitud, double longitud ){

        if(longitud == 0 && latitud == 0){
            Toast.makeText(getApplicationContext(), "Registro exitoso sin ubicación", Toast.LENGTH_SHORT).show();
        }

        db.abrir();
        db.insertarregis(nClinica, nom, correo, cont, espe, direc,hora, tele, longitud, latitud);
        db.cerrar();

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


    public Loader<Object> onCreateLoader(int id, Bundle args) {
        return null;
    }


    public void onLoadFinished(Loader<Object> loader, Object data) {

    }


    public void onLoaderReset(Loader<Object> loader) {

    }

    private void actualizarUsuario(Double longitud,Double latitud) {
        SQLiteDatabase db = this.db.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("Longitud", longitud);
        valores.put("Latitud", latitud);
        valores.put("Telefono", tele);
        valores.put("NombreClinica", nClinica);

        valores.put("Nombre", nom);
        valores.put("Direccion", direc);
        valores.put("Telefono", tele);
        valores.put("HorarioAtencion", hora);

        SharedPreferences preferencias = getSharedPreferences("preferenciaLogin",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString("clinicas",nClinica);
        editor.putString("nombre",nom);
        editor.putString("horario",hora);
        editor.putString("direccion",direc);
        editor.putString("telefono",tele);
        editor.apply();

        final Integer id = preferencias.getInt("id_user",0);


        db.rawQuery("update registros set NombreClinica =  '" + nClinica + "', Nombre='" + nom + "' , Direccion = '" +dir + "',HorarioAtencion='" +hora+ "', Telefono=" + tele+ " WHERE ID = " + id + ";", null);



        // update("registros", valores,"ID ="+id,null);
        Toast.makeText(getApplicationContext(),"Datos Actualizados",Toast.LENGTH_LONG).show();
        db.close();


    }

    private SQLiteDatabase getWritableDatabase() {
        return null;
    }


}


