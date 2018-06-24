package com.example.danny.kodax1.conexion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.danny.kodax1.R;

import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RegistroPost extends AppCompatActivity {

    String nombreCli, nombre1, telefono;
    EditText nameC, name,  tele;
    Button envia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_post);

        //RequestQueue requ = Volley.newRequestQueue(getApplicationContext());
        final String  link = "https://kodaxpro.000webhostapp.com/BDRemota1/prueba1.php";


        nameC = (EditText)findViewById(R.id.namecli);
        name = (EditText)findViewById(R.id.name);
        tele = (EditText)findViewById(R.id.tel);
        envia = (Button)findViewById(R.id.enviar);

        nombreCli = nameC.getText().toString();
        nombre1 = name.getText().toString();
        telefono = tele.getText().toString();

        envia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest StringRe = new StringRequest(Request.Method.POST, link,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                })
                {
                    @Override
                    protected Map<String,String> getParams(){


                        Map<String,String> para = new HashMap<String, String>();
                        para.put("nameCli", nombreCli);
                        para.put("name", nombre1);
                        para.put("tele", telefono);

                        return para;
                    }
                };

                int socketTimeout = 990000;//tiempo de espera
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                StringRe.setRetryPolicy(policy);
                queue.add(StringRe);
               /* enviar envi = new enviar();

                try {
                String reponse = envi.execute("https://kodaxpro.000webhostapp.com/BDRemota1/prueba1.php?nameCli="+nameC.getText().toString()+"&name="+name.getText().toString()+"&tel="+tele.getText().toString()).get();

                    if (reponse != null){
                        Toast.makeText(getApplicationContext(), "guardado exitoso", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){

                }*/
            }
        });
    }



    public void Obtener() throws UnsupportedEncodingException{

        nombreCli = nameC.getText().toString();
        nombre1 = name.getText().toString();
        telefono = tele.getText().toString();


        String data = URLEncoder.encode("nameCli", "UTF-8")
                +"="+URLEncoder.encode(nombreCli, "UTF-8");

        data += "&" + URLEncoder.encode("name", "UTF-8")
                + "=" + URLEncoder.encode(nombre1, "UTF-8");

        data += "&" + URLEncoder.encode("tele", "UTF-8")
                + "=" + URLEncoder.encode(telefono, "UTF-8");

        String text = "";
        BufferedReader reader = null;

        try {

            URL url = new URL("https://kodaxpro.000webhostapp.com/BDRemota1/prueba1.php");

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine())!=null){
                sb.append(line + "\n");
            }

            text = sb.toString();

        }catch (Exception e){

        }

        finally {
            try {
                reader.close();
            }catch (Exception e){

            }
        }



    }
}
