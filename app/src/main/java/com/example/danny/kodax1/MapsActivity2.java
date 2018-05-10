package com.example.danny.kodax1;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.danny.kodax1.Usuarios.Usuario;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DataBase db;
    ArrayList<Usuario> usuarios;
    Button button;
    LatLng punto;

    static double mlong;
    static double mlati;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        db = new DataBase(this, "BD1", null, 1);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

            if (status == ConnectionResult.SUCCESS) {
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            } else {
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity) getApplicationContext(), 10);
                dialog.show();
            }
        }
        button = (Button) findViewById(R.id.btnSaveUbicacion);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity2.this);
                builder.setTitle("Ubicación");
                builder.setMessage("Seguro que ésta es la ubicación de su clínica?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getParent(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
                        } else {
                            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                            if (location != null) {
                                double latti = location.getLatitude();
                                double longi = location.getLongitude();

                                Intent intent = new Intent();
                                intent.putExtra("latitud_key", mlati);
                                intent.putExtra("longitud_key", mlong);
                                setResult(RESULT_OK,intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Enciende la ubicación o tu móvil no tiene gps",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                Dialog dialog = builder.create();
                dialog.show();


            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();

                LatLng sydney = new LatLng(latti, longi);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Clinica medica Danli").
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        mMap.clear();
                        punto = latLng;
                        mlong = punto.longitude;
                        mlati = punto.latitude;


                        Toast.makeText(MapsActivity2.this,"punto " +punto,Toast.LENGTH_SHORT).show();

                        mMap.addMarker(new MarkerOptions().position(punto));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(punto, 16));
                        button.setEnabled(true);
                    }
                });



                float zoonlevel = 20;

                //TRAER LATITUD Y LONGITUD BD
              /*  SQLiteDatabase db1 = db.getReadableDatabase();
                Usuario u = null;
                Bundle bundle = getIntent().getExtras();
                int id = bundle.getInt("id_key", 0);
                usuarios =  new ArrayList<Usuario>();
                Cursor c = db1.rawQuery("SELECT Latitud, Longitud FROM registros where ID = "+id+";", null);*/

                /*
                * Longitud      Latitud*
                * 67587658      4765447
                */
               /* c.moveToNext();
                double latitudDB = c.getDouble(0);;
                double longitudDB = c.getDouble(1);

                LatLng sydney2 = new LatLng(latitudDB, longitudDB);
                mMap.addMarker(new MarkerOptions().position(sydney2).title("Clinica medica Katling").icon
                (BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                float zoonlevel2 = 20;*/





                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoonlevel));








                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney2, zoonlevel2));

            } else {


                Toast.makeText(getApplicationContext(), "Enciende la ubicación o tu móvil no tiene gps", Toast.LENGTH_LONG).show();
            }
        }
    }

}
