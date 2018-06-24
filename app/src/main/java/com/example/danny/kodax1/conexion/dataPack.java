package com.example.danny.kodax1.conexion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Danny on 22/6/2018.
 */

public class dataPack {

    String nombreCli, nombre, telef;

    public dataPack(String nombreCli, String nombre, String telef) {
        this.nombreCli = nombreCli;
        this.nombre = nombre;
        this.telef = telef;
    }


    public String packData(){

        JSONObject jo = new JSONObject();
        StringBuffer packedData = new StringBuffer();

        try {

            jo.put("nameCli", nombreCli);
            jo.put("name", nombre);
            jo.put("tele", telef );

            Boolean firstValue = true;
            Iterator it = jo.keys();

            do {
                String key= it.next().toString();
                String value = jo.get(key).toString();

                if (firstValue){
                    firstValue = false;

                }else {
                    packedData.append("&");
                }

                packedData.append(URLEncoder.encode(key, "UTF-8"));
                packedData.append("=");
                packedData.append(URLEncoder.encode(value, "UTF-8"));


            }while (it.hasNext());

            return packedData.toString();

        }catch (JSONException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return null;
    }
}
