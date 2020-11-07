package com.example.androidlocais;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.gcm.Task;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;

public class Tela2 extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    private final LatLng UFV = new LatLng(-20.760839, -42.870163);
    private final LatLng Curitiba = new LatLng(-25.448726, -49.260824);
    private final LatLng Vicosa = new LatLng(-20.761062, -42.878634);
    private GoogleMap map;
    public Marker marcador = null;
    public LocationManager lm;
    public Criteria criteria;
    public String provider;
    private final int LOCATION_PERMISSION = 1;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.tela2);
        Log.d("Estado atual de ", getClass().getName() + "= .onCreate");

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa)).getMapAsync(this);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();

        PackageManager packageManager = getPackageManager();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION);

        boolean hasGPS = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
        if (hasGPS) {
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            Log.d("GPS", "TEM");
        } else {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            Log.d("GPS", "NAO TEM");
        }
    }

    public void Mudamapa(View v) {
        TextView local = (TextView) v;
        int tag = Integer.parseInt(v.getTag().toString());
        atualizar(tag);
    }

    public void atualizar(int tag) {
        switch (tag) {
            case 0: {
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(Curitiba, 18);
                map.animateCamera(update);
                break;
            }
            case 1: {
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(Vicosa, 19);
                map.animateCamera(update);
                break;
            }
            case 2: {
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(UFV, 18);
                map.animateCamera(update);
                break;
            }
        }
    }

    public void Localiza(View view) {
        provider = lm.getBestProvider(criteria, true);
        if (provider == null) {
            Log.d("Provedor", "Nenhum provedor encontrado");
        } else {
            lm.requestLocationUpdates(provider, 900000000, 0, this);
            lm.removeUpdates(listener);
        }
    }

    protected void onStart() {
        super.onStart();
        Log.d("Estado atual de ", getClass().getName() + "= .onStart");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case LOCATION_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permissão concedida", 5000);
                else {
                    Toast.makeText(this, "Permissão Necessária para saber seu local e calcular a distância", 5000);
                    Log.d("PERMISSAO", "NAO DEIXOUUUUUUU");
                }
            }
        }
    }

    protected void onResume(){
        super.onResume();
        Log.d("Estado atual de ", getClass().getName() + "= .onResume");
    }

    protected void onRestart(){
        super.onRestart();
        Log.d("Estado atual de ", getClass().getName() + "= .onRestart");
    }

    protected void onPause(){
        super.onPause();
        Log.d("Estado atual de ", getClass().getName() + "= .onPause");
    }

    protected void onDestroy(){
        lm.removeUpdates(this);
        super.onDestroy();
        Log.d("Estado atual de ", getClass().getName() + "= .onDestroy");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().position(UFV).title("UFV"));
        map.addMarker(new MarkerOptions().position(Curitiba).title("Minha casa Curitiba"));
        map.addMarker(new MarkerOptions().position(Vicosa).title("Meu apt Viçosa"));

        Intent it = getIntent();
        Bundle option = it.getExtras();
        Toast.makeText(this, option.getString("texto"), 5000).show();
        int opcao = option.getInt("opcao");
        atualizar(opcao);
    }

    private final LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {

        }
    };

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //Log.d("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Location casaVicosa = new Location(provider);
        casaVicosa.setLatitude(Vicosa.latitude);
        casaVicosa.setLongitude(Vicosa.longitude);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng ATUAL = new LatLng(latitude, longitude);
        double dist = location.distanceTo(casaVicosa)/1000;
        DecimalFormat df = new DecimalFormat("0.##");

        if (marcador!=null)
            marcador.remove();
        marcador = map.addMarker(new MarkerOptions().position(ATUAL).
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ATUAL, 19);
        map.animateCamera(update);
        Toast.makeText(this, "Distância do apt em Viçosa: "+ df.format(dist) + "km", 5000).show();
        lm.removeUpdates(listener);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("AAAAAAAAAAAAAAA", "onProviderEnabled: ");
    }
    @Override
    public void onProviderDisabled(String provider) {
        Log.d("BBBBBBBBBBBBBBB", "onProviderDisabled: ");
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle Extras) {
        Log.d("CCCCCCCCCCCCCCC", "onStatusChanged: ");
    }
}

