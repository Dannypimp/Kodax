package com.example.danny.kodax1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.danny.kodax1.Usuarios.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class Login extends AppCompatActivity {

    TextView tvRegist;
    CardView botonIngre;

    String correo, pass;
    EditText txtpass, txtusu;

    DataBase db1 = new DataBase(this,"BD1",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //-------------danny,

        txtusu = (EditText)findViewById(R.id.editText);
        txtpass = (EditText)findViewById(R.id.editText2);
        correo = txtusu.getText().toString();
        pass = txtusu.getText().toString();



        /*tvRegist = (TextView)findViewById(R.id.textView3);

        tvRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Registro.class);
                startActivity(i);
            }
        });*/

        botonIngre = (CardView) findViewById(R.id.button);

        botonIngre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (txtusu.getText().toString().isEmpty()  ){

                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                    txtusu.requestFocus();
                }else if (txtpass.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                    txtpass.requestFocus();
                }else{

                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            final String res = iniciarSesion(correo, pass);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int r = jsonO(res);
                                    if (r>0){
                                        Intent i = new Intent(getApplicationContext(), RegistroEditable.class);
                                        startActivity(i);
                                    }else {
                                        Toast.makeText(getApplicationContext(), "incorrecto", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    };


                }
                /*

                if(txtusu.getText().toString().isEmpty() || txtusu.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                } else {


                    try {

                        if(txtpass.getText().equals("") || txtusu.getText().equals("")){
                            Toast.makeText(getApplicationContext(), "por favor llene los campos", Toast.LENGTH_LONG).show();
                        }else {
                            Cursor cursor=db1.consultLogin(txtusu.getText().toString(),txtpass.getText().toString());
                            if (cursor != null){
//https://stackoverflow.com/questions/41418361/android-login-using-php-mysql-and-volley
                                Usuario u = null;

                                ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

                                while (cursor.moveToNext()){
                                    u = new Usuario();
                                    u.setNombre(cursor.getString(1));
                                    u.setDireccion(cursor.getString(2));
                                    u.setNombreClinica(cursor.getString(0));
                                    u.setTelefono(cursor.getString(4));
                                    u.setHorario(cursor.getString(3));
                                    u.setId(cursor.getInt(5));
                                    usuarios.add(u);
                                }

                                SharedPreferences preferencias = getSharedPreferences("preferenciaLogin",MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferencias.edit();
                                editor.putString("sesion","si");
                                int id = u.getId();
                                editor.putInt("id_user", id);
                                editor.putString("clinicas",u.getNombreClinica());
                                editor.putString("nombre",u.getNombre());
                                editor.putString("horario",u.getHorario());
                                editor.putString("direccion",u.getDireccion());
                                editor.putString("telefono",u.getTelefono());
                                editor.apply();


                                Intent i = new Intent(getApplicationContext(),perfil.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("usuario", usuarios.get(0));
                                i.putExtras(bundle);
                                startActivity(i);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Correo y/o contrasena incorrecta", Toast.LENGTH_LONG).show();
                            }

                            txtusu.setText("");
                            txtpass.setText("");
                            txtusu.findFocus();
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }

                */

            }
        });

    }

    public String iniciarSesion(String corre, String contra) {

        String parametros ="corre="+corre+"&contra="+contra;

        HttpURLConnection connection = null;
        String respuesta = "";

        try {
            URL url = new URL("https://kodaxpro.000webhostapp.com/BDRemota1/Update.php");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length", ""+ Integer.toString(parametros.getBytes().length));

            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner instream = new Scanner(connection.getInputStream());

            while (instream.hasNextLine()){
                respuesta+=(instream.nextLine());


            }

        }catch (Exception e){

        }

        return respuesta.toString();

    }

    public int jsonO(String rpt){

        int res = 0;

        try {
            JSONArray jsonArray = new JSONArray(rpt);
            if (jsonArray.length()>0){
                res = 1;

            }

        }catch (Exception e){

        }

        return res;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.registros){
            Intent i = new Intent(getApplicationContext(),Registro.class);
            startActivity(i);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
