package com.example.marko.cargo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.DataAll;
import com.example.Lokacija;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission_group.LOCATION;

public class PregledLokacij extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ApplicationMy app;
    DataAll da;
    List<String> naslovi = new ArrayList<String>();
    List<Lokacija> loc;
    Geocoder geo;
    private Location myloc;
    int najblizja = -1;
    public LocationManager locman;
    public List<LatLng> dolzine;
    public List<Marker> podatki;
    Lokacija lok;
    Bundle extras;

    //int LOCATION_REFRESH_TIME = 1000;
    //int LOCATION_REFRESH_DISTANCE = 5;

    //locman= (LocationManager) getSystemService(LOCATION_SERVICE);
    //locman.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION REFRESH_TIME, LOCATION_REFRESH_DISTANCE, mLocationListener);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (ApplicationMy) getApplication();
        loc = app.getLokacijaAll();
        extras = getIntent().getExtras();
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //for(Lokacija l : loc) {
        //    Marker dummy;
        //    naslovi.add(l.getName());
        //    podatki.add(dummy.setTag(l));
        //}

        setContentView(R.layout.activity_pregled_lokacij);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;

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
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        for(Lokacija l: loc) {
            LatLng address = getLocationFromAddress(this,l.getName());
            mMap.addMarker(new MarkerOptions().position(address).title(l.getName())).setTag(l);
        }

        /*mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Lokacija x = (Lokacija)marker.getTag();
                String id = x.getId();
                Intent i = new Intent(getBaseContext(), Podrobno.class);
                i.putExtra("Nekaj", extras.getString(id));
                startActivity(i);
                return true;
            }
        });*/
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
