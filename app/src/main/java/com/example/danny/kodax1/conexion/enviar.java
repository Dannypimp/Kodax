package com.example.danny.kodax1.conexion;

import android.os.AsyncTask;

import com.android.volley.toolbox.HttpClientStack;

import org.apache.http.params.HttpConnectionParams;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Danny on 22/6/2018.
 */

public class enviar extends AsyncTask<String,String,String> {


    @Override
    protected String doInBackground(String... strings) {


        HttpURLConnection urlConn = null;
        URL url = null;
        try {
            url = new URL(strings[0]);

        }catch (MalformedURLException e){

        }

        try {
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.connect();
            int code = urlConn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream( urlConn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                StringBuffer bufer = new StringBuffer();
                while ((line = reader.readLine()) != null){
                    bufer.append(line);
                }

                return bufer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
