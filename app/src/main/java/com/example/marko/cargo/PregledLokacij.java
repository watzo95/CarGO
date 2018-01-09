package com.example.marko.cargo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
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
import com.google.android.gms.maps.model.PolylineOptions;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
    private Boolean EMULATOR;
    private TextView koncnaRazdalja;

    //TSP

    private final int THRESHOLD = 30; // Max number of destinations on the map
    private boolean solveInProgress = false; // Flag for GA_Task or SA_Task being in progress
    private AsyncTask solverTask; // Reference to the GA_Task or SA_Task that is in progress
    private ArrayList<Polyline> polylines = new ArrayList();
    private int publishInterval = 333; // defines publishing rate in milliseconds

    // initialize options drawer
    private SharedPreferences mSharedPreferences;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;

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

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        koncnaRazdalja = (TextView) findViewById(R.id.final_distance);
        //addDrawerItems(R.array.menuItems, mDrawerList);
        //mDrawerList.setOnItemClickListener(new SideDrawerClickListener());

        ApplicationInfo ai = null;
        try {
            ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            EMULATOR = bundle.getBoolean("emulator", false);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_pregled_lokacij);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if(mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            if (mMap != null) {
                setUpMap();
            }
        }

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
        TourManager tm = new TourManager();
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        for(Lokacija l: loc) {
            Marker m;
            LatLng address = getLocationFromAddress(this,l.getName());
            //mMap.addMarker(new MarkerOptions().position(address).title(l.getName())).setTag(l);
            ArrayList<Destinacija> lista = new ArrayList<Destinacija>();
            m = mMap.addMarker(new MarkerOptions().position(address));
            lista.add(new Destinacija(m));
            tm.addDestination(new Destinacija(m));
        }

        /*mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //Lokacija x = (Lokacija)marker.getTag();
                //String id = x.getId();
                Intent i = new Intent(getBaseContext(), Podrobno.class);
                //i.putExtra("Nekaj", extras.getString(id));
                startActivity(i);
                return true;
            }
        });*/
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    //TSP dodatek
    //=============================================================================================================================

    private void setUpMap() {

        // Setting a click event handler for the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if (TourManager.numberOfDestinations() >= THRESHOLD || solveInProgress) {
                    return;
                }
                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

//                // Animating to the touched position
//                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                Marker marker = mMap.addMarker(markerOptions);
                // Add the new Destination/Marker to TourManager and to mapMarkers
                TourManager.addDestination(new Destinacija(marker));
            }
        });

        // Disable clicking on markers
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return true;
            }
        });

    }

    public void clearMap(View view) {

        // if Async in progress, need to press twice to clear map
        if (solveInProgress) solverTask.cancel(true);
        else {
            mMap.clear();
            TourManager.removeAll();
            System.gc();
        }
    }

    public void graphMap(Tour tour) {

        ArrayList<Marker> markers = new ArrayList<Marker>();
        for (Destinacija D: tour.getAllDest()) {
            markers.add(D.getMarker());
        }

        if (markers.size()==0) return;

        // remove existing polylines
        for(Polyline pl : polylines) {
            pl.remove();
        }

        ArrayList<LatLng> latLngs = new ArrayList<>();
        for (Marker M: markers) {
            latLngs.add(M.getPosition());
        }
        latLngs.add(markers.get(0).getPosition());
        PolylineOptions poly = new PolylineOptions().addAll(latLngs);

        polylines.add(mMap.addPolyline(poly));
    }

    public void TSP_GA(View view) {
        if (TourManager.numberOfDestinations()==0 || solveInProgress) return;

        GA_Task task = new GA_Task();
        solverTask = task;
        task.execute();

        System.gc();
    }

    class GA_Task extends AsyncTask<Void, Tour, Population> {

        Tour bestTourSoFar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            solveInProgress = true;

            //change color of button to indicate processing
            //Button GA_button = (Button) findViewById(R.id.graphGAButton);
            //GA_button.setBackgroundColor(0xb0FF9933);

            // change text of clear button
            //Button button = (Button) findViewById(R.id.clearButton);
            //button.setText("STOP");
        }

        @Override
        protected Population doInBackground(Void... voids) {

            // Initialization
            int popSize = Integer.parseInt(mSharedPreferences.getString("popSize", "50"));
            int generations = Integer.parseInt(mSharedPreferences.getString("generations", "200"));

            Population pop = new Population(popSize, true);
            Tour fittest = pop.getFittest();
            publishProgress(fittest);

            long time = System.currentTimeMillis();
            long lastPublishTime = time;

            for (int i = 0; i < generations; i++) {
                if ( isCancelled() ) break;

                time = System.currentTimeMillis();
                pop = GA.evolvePopulation(pop, mSharedPreferences);
                bestTourSoFar = new Tour(pop.getFittest());
                if (time - lastPublishTime > publishInterval) {
                    lastPublishTime = time;
                    publishProgress(pop.getFittest());
                }
            }
            return pop;
        }

        @Override
        protected void onProgressUpdate(Tour... tours) {
            super.onProgressUpdate(tours[0]);
            Tour currentBest = tours[0];
            graphMap(currentBest);
            System.out.println("Current distance: " + currentBest.getDistance());

        }

        @Override
        protected void onPostExecute(Population pop) {
            super.onPostExecute(pop);
            Tour fittest = pop.getFittest();
            graphMap(fittest);
            System.out.println("GA Final distance: " + pop.getFittest().getDistance());

            // Display final distance
            TextView tv1 = (TextView) findViewById(R.id.final_distance);
            int finalDistance = (int) pop.getFittest().getDistance();
            tv1.setText("Optimalna pot: " + finalDistance + " km");

            //change color of button to indicate finish
            //Button GA_button = (Button) findViewById(R.id.graphGAButton);
            //GA_button.setBackgroundColor(0xb0ffffff);

            // change text of clear button
            //Button button = (Button) findViewById(R.id.clearButton);
            //button.setText("CLEAR");

            pop = null;
            solveInProgress = false;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            // Display final distance
            //TextView tv1 = (TextView) findViewById(R.id.final_distance);
            int finalDistance = (int) bestTourSoFar.getDistance();
            //tv1.setText("FINAL DISTANCE: " + finalDistance + " km");

            //change color of button to indicate finish
            //Button GA_button = (Button) findViewById(R.id.graphGAButton);
            //GA_button.setBackgroundColor(0xb0ffffff);

            // change text of clear button
            //Button button = (Button) findViewById(R.id.clearButton);
            //button.setText("CLEAR");

            solveInProgress = false;
        }
    }

}
