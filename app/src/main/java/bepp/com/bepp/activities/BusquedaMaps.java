package bepp.com.bepp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.DireccionEnvioActivity;

public class BusquedaMaps extends  FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private TextView streetText, cityText, zipCodeText;
    private String lugar;
    private double latitudeGner = 19.432608;
    private   double longitudeGner = -99.133209;
    private LatLng landGner;
    static final LatLng MEXICO = new LatLng(19.432608, -99.133209);
    static  LatLng POSICION ;
    private LatLng mCenterLatLong;
    private List<Address> addresses;
    private Geocoder geocoder;
    private LatLng center;
    private static LatLng mPosition;
    private static float mZoom;
    private FloatingActionButton btnMyLocation;

    private CountDownTimer mDragTimer;
    private boolean mTimerIsRunning = false;
    private  Button btnDireccion;

    private static String cp;
    private static String calle;
    private static String numeroExt;
    private static  String colonia;
    private static String ciudad;
    private static String municipio;
    private static  String estado;
    private static  String pais;

    private static  String longitud ;
    private static  String latitud ;






    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_maps);



        btnDireccion = (Button) findViewById(R.id.btn_direccion);
        streetText = (TextView) findViewById(R.id.street);
        cityText = (TextView) findViewById(R.id.city);
        zipCodeText = (TextView) findViewById(R.id.zipCode);
        //btnMyLocation = (FloatingActionButton) findViewById(R.id.btnFloatingAction);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());


        btnDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nvaDireccionIntent = new Intent(BusquedaMaps.this, DireccionEnvioActivity.class);
                nvaDireccionIntent.putExtra("cp", cp);
                nvaDireccionIntent.putExtra( "calle", calle);
                nvaDireccionIntent.putExtra( "numeroExt", numeroExt);
                nvaDireccionIntent.putExtra( "colonia", colonia);
                nvaDireccionIntent.putExtra( "ciudad", ciudad);
                nvaDireccionIntent.putExtra( "municipio", municipio);
                nvaDireccionIntent.putExtra( "estado", estado);
                nvaDireccionIntent.putExtra( "pais", pais);
                nvaDireccionIntent.putExtra( "latitud", latitud);
                nvaDireccionIntent.putExtra( "longitud", longitud);
                startActivity(nvaDireccionIntent);
            }
        });



        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .setCountry("MEX")
                .build();



        mDragTimer = new CountDownTimer(1000, 1) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                mTimerIsRunning = false;
            }
        };


        autocompleteFragment.setFilter(typeFilter);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                POSICION = place.getLatLng();


                try
                {
                    addresses = geocoder.getFromLocation(POSICION.latitude, POSICION.longitude, 1);


                    if (addresses != null && addresses.size() > 0)
                    {
                        String address = addresses.get(0).getAddressLine(0);
                        String[] parts = address.split(",");

                        colonia = parts[1].trim();

                        ciudad = addresses.get(0).getLocality();
                        //get current province/City
                        String province = addresses.get(0).getAdminArea();
                        latitud = String.valueOf(POSICION.latitude);
                        longitud = String.valueOf(POSICION.longitude);

                        //get country
                        streetText.setText(calle +" ," +municipio);
                        cityText.setText(pais);

                        //get postal code
                        cp = addresses.get(0).getPostalCode();
                        estado = addresses.get(0).getLocality();
                        calle = addresses.get(0).getThoroughfare();


                        //get place Name
                        numeroExt = addresses.get(0).getFeatureName(); // Only if available else return NULL

                        mCenterLatLong = mMap.getCameraPosition().target;

                        //move map camera
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(POSICION));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(19));

                    }
                }
                catch (IOException e) {
                }

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
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

        Log.e("BeppMap", "onMapReady");


        mMap = googleMap;
        CameraUpdate point = CameraUpdateFactory.newLatLng(new LatLng(19.320931, -99.4328046));

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(point);
        mMap.animateCamera(point);
        mMap.clear();


        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                Log.e("BeppMap", "onCameraMoveStarted");

                mTimerIsRunning = true;
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                Log.e("BeppMap", "onCameraIdle");


                mTimerIsRunning = false;
                // Cleaning all the markers.
                if (mMap != null) {
                    mMap.clear();
                }

                mPosition = mMap.getCameraPosition().target;
                Log.e("BeppMap", " mPosition " +mPosition);


                try
                {
                    Log.e("BeppMap", "mPosition.latitude : " +mPosition.latitude);
                    Log.e("BeppMap", "mPosition.longitude : " +mPosition.longitude);

                    addresses = geocoder.getFromLocation(mPosition.latitude, mPosition.longitude, 1);

                    if (addresses != null && addresses.size() > 0)
                    {

                        Log.e("BeppMap", "addresses : " +addresses);

                        Log.e("BeppMap", "addressesto : " +addresses.toString());

                        String address = addresses.get(0).getAddressLine(0);
                        String[] parts = address.split(",");

                        colonia = parts[1].trim();

                        ciudad = addresses.get(0).getLocality();
                        //get current province/City
                        String province = addresses.get(0).getAdminArea();
                        latitud = String.valueOf(mPosition.latitude);
                        longitud = String.valueOf(mPosition.longitude);

                        //get country

                        //get postal code
                        cp = addresses.get(0).getPostalCode();
                        estado = addresses.get(0).getAdminArea();

                        if(estado.equals("Ciudad de México")){
                            municipio = "CDMX";
                        }else{
                        municipio = addresses.get(0).getSubAdminArea();
                        }

                        calle = addresses.get(0).getThoroughfare();

                        streetText.setText(calle +" ," +municipio);
                        cityText.setText(pais);



                        //get place Name
                        numeroExt = addresses.get(0).getFeatureName(); // Only if available else return NULL







                        //move map camera
                        //mMap.moveCamera(CameraUpdateFactory.newLatLng(mPosition));
                        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                    }
                }
                catch (IOException e) {
                }



                mZoom = mMap.getCameraPosition().zoom;

                if (mTimerIsRunning) {
                    mDragTimer.cancel();
                }

                mDragTimer.start();
                mTimerIsRunning = true;


            }
        });




        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.e("BeppMap", "setOnMapClickListener");


                try {

                    LatLng latLng1 = new LatLng(center.latitude,
                            center.longitude);
                    Log.e("BeppMap", "latLng1 :" +latLng1);
                    try
                    {
                        Log.e("BeppMap", "latLng1 :" +latLng1.longitude);

                        addresses = geocoder.getFromLocation(latLng1.latitude, latLng1.longitude, 1);
                        if (addresses != null && addresses.size() > 0)
                        {
                            String address = addresses.get(0).getAddressLine(0);
                            String[] parts = address.split(",");
                            calle = parts[0].replaceAll("[0-9]","").trim();
                            colonia = parts[1].trim();


                            String part3 = parts[2];
                            municipio = part3.replaceAll("[0-9]","").trim();
                            estado = parts[3];
                            pais = parts[4];

                            latitud = String.valueOf(latLng1.latitude);
                            longitud = String.valueOf(latLng1.longitude);



                            ciudad = addresses.get(0).getLocality();
                            //get current province/City
                            String province = addresses.get(0).getAdminArea();

                            //get country
                            streetText.setText(calle +" ," +municipio);
                            cityText.setText(pais);

                            //get postal code
                            cp = addresses.get(0).getPostalCode();

                            //get place Name
                            numeroExt = addresses.get(0).getFeatureName(); // Only if available else return NULL


                            //move map camera
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                        }
                    }
                    catch (IOException e) {
                    }

                    Marker m = mMap.addMarker(new MarkerOptions()
                            .position(latLng1)
                            .title(" Set your Location ")
                            .snippet("")
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.pin_map)));
                    m.setDraggable(true);


                } catch (Exception e) {
                }


            }
        });



        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {



                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);


            }
        }
        else {




            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);




        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10 * 1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {


        LatLng latLng;
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();

        }








        double  latitudePosicion ;
        double longitudePosicion ;


        if(POSICION != null ){
            latitudePosicion  = POSICION.latitude;
            longitudePosicion = POSICION.longitude;

        }else {

            latitudePosicion  = location.getLatitude();;
            longitudePosicion = location.getLongitude();;
        }

        latLng = new LatLng(latitudePosicion, longitudePosicion);


        mCenterLatLong = mMap.getCameraPosition().target;
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try
        {
            addresses = geocoder.getFromLocation(latitudePosicion, longitudePosicion, 1);
            if (addresses != null && addresses.size() > 0)
            {
                String address = addresses.get(0).getAddressLine(0);
                String[] parts = address.split(",");
                calle = parts[0].replaceAll("[0-9]","").trim();
                colonia = parts[1].trim();


                String part3 = parts[2];
                municipio = part3.replaceAll("[0-9]","").trim();
                estado = parts[3];
                pais = parts[4];



                latitud = String.valueOf(latitudePosicion);
                longitud = String.valueOf(longitudePosicion);



                ciudad = addresses.get(0).getLocality();
                //get current province/City
                String province = addresses.get(0).getAdminArea();

                //get country
                streetText.setText(calle +" ," +municipio);
                cityText.setText(pais);

                //get postal code
                cp = addresses.get(0).getPostalCode();

                //get place Name
                numeroExt = addresses.get(0).getFeatureName(); // Only if available else return NULL



                //move map camera
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                //stop location updates
                if (mGoogleApiClient != null) {
                    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                }
            }
        }
        catch (IOException e) {
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    public void onZoom(View view)
    {
        if(view.getId() == R.id.Bzoomin)
        {
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
        }
        if(view.getId() == R.id.Bzoomout)
        {
            mMap.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }


    }


    /*public void onSearch(View view) {




        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(POSICION);
        markerOptions.title("Posición");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);


    }*/




}