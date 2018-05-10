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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danny.kodax1.Usuarios.Usuario;

import java.util.ArrayList;


public class Login extends AppCompatActivity {

    TextView tvRegist;
    CardView botonIngre;


    DataBase db1 = new DataBase(this,"BD1",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



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


                EditText txtusu = (EditText)findViewById(R.id.editText);
                EditText txtpass = (EditText)findViewById(R.id.editText2);

                if(txtusu.getText().toString().isEmpty() || txtusu.getText().toString().isEmpty()){
                     Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                } else {


                    try {

                        if(txtpass.getText().equals("") || txtusu.getText().equals("")){
                            Toast.makeText(getApplicationContext(), "por favor llene los campos", Toast.LENGTH_LONG).show();
                        }else {
                            Cursor cursor=db1.consultLogin(txtusu.getText().toString(),txtpass.getText().toString());
                            if (cursor != null){

                                Usuario u = null;

                                ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

                                while (cursor.moveToNext()){
                                    u = new Usuario();
                                    u.setNombre(cursor.getString(2));
                                    u.setDireccion(cursor.getString(6));
                                    u.setNombreClinica(cursor.getString(1));
                                    u.setTelefono(cursor.getString(8));
                                    u.setHorario(cursor.getString(7));
                                    u.setId(cursor.getInt(0));
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


            }
        });

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
