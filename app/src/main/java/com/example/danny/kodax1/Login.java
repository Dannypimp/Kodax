package com.example.danny.kodax1;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Login extends AppCompatActivity {

    TextView tvRegist;
    Button botonIngre;


    DataBase db1 = new DataBase(this,"BD1",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        tvRegist = (TextView)findViewById(R.id.textView3);

        tvRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Registro.class);
                startActivity(i);
            }
        });

        botonIngre = (Button)findViewById(R.id.button);

        botonIngre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtusu = (EditText)findViewById(R.id.editText);
                EditText txtpass = (EditText)findViewById(R.id.editText2);

                try {
                    Cursor cursor=db1.consultLogin(txtusu.getText().toString(),txtpass.getText().toString());
                    if (cursor.getCount()>0){
                        Intent i = new Intent(getApplicationContext(),Principal.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(), "Correo y/o contrasena incorrecta", Toast.LENGTH_LONG).show();
                    }
                    txtusu.setText("");
                    txtpass.setText("");
                    txtusu.findFocus();

                }catch (SQLException e){
                    e.printStackTrace();
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
