package com.example.danny.kodax1.conexion;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by Danny on 22/6/2018.
 */

public class connector {

    public static HttpURLConnection connection(String urlAddres){

        try {
            URL url = new URL(urlAddres);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(20000);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            return conn;

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }

        return null;
    }

}
