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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Response;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.READ_CONTACTS;



public class Registro extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object>, com.android.volley.Response.Listener, com.android.volley.Response.ErrorListener {

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
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
    CardView regist;

    String nClinica, nom, correo, cont, direc,hora, tele, espe;
    DataBase db = new DataBase(this,"BD1",null,1);



    private static final int REQUEST_READ_CONTACTS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        requestQueue =  Volley.newRequestQueue(getApplicationContext());
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

        nombreClinica = (EditText) findViewById(R.id.edit0);
        nombre = (EditText) findViewById(R.id.edit1);
        corre = (EditText) findViewById(R.id.edit2);
        contra = (EditText) findViewById(R.id.edit3);
        esp = (Spinner) findViewById(R.id.sp_especial);
        dir = (EditText) findViewById(R.id.edit5);
        hor = (EditText) findViewById(R.id.edit7);
        tel = (EditText) findViewById(R.id.edit6);
        regist = (CardView) findViewById(R.id.boton);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.esp, R.layout.spinner_item_espe);
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


                } else if (validarSintaxisCorreo(corre.getText().toString())) {

                    nClinica = nombreClinica.getText().toString();
                    nom = nombre.getText().toString();
                    correo = corre.getText().toString();
                    cont = contra.getText().toString();
                    direc = dir.getText().toString();
                    hora = hor.getText().toString();
                    tele = tel.getText().toString();
                    espe = esp.getSelectedItem().toString();

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

                } else {

                    Toast.makeText(getApplicationContext(), "Correo Invalido", Toast.LENGTH_SHORT).show();
                }
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


        String url = "https://kodaxpro.000webhostapp.com/BDRemota1/JSONregistros.php?nombre_clinica="+nClinica+"&nombre="+nom+"&correo="+correo+"&contrasena="+cont+"&especialidad="+espe+"&direccion="+direc+"&horario="+hora+"&telefono="+tele+"&latitud="+latitud+"&longitud="+longitud;

        url = url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);

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

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }


    private void actualizarUsuario() {
        SQLiteDatabase db = this.db.getWritableDatabase();
        String []parametros = {nombre.getText().toString()};
        ContentValues valores = new ContentValues();


        valores.put("Telefono", tele);
        valores.put("Contraseña", cont);
        valores.put("NombreClinica", nClinica);
        valores.put("Correo", correo);
        valores.put("Nombre", nom);
        valores.put("Direccion", direc);
        valores.put("Telefono", tele);
        valores.put("HorarioAtencion", hora);



        this.getWritableDatabase().update("registros", valores,nom +"=?",new String[]{"delmi ash"});
        Toast.makeText(getApplicationContext(),"Datos Actualizados",Toast.LENGTH_LONG).show();
        db.close();


    }

    private SQLiteDatabase getWritableDatabase() {
        return null;
    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(), "no se ha registrado correctamente"+error.toString(), Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString() );
    }

    @Override
    public void onResponse(Object response) {

        Toast.makeText(getApplicationContext(), "se ha registrado correctamente", Toast.LENGTH_SHORT).show();

    }
}