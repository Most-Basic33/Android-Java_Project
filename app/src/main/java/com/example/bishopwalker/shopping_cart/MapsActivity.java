package com.example.bishopwalker.shopping_cart;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED;
    private static final String ACCESS_COURSE_LOCATION=Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private PlaceDetectionClient mPlaceDetectionClient;
    //Entry Point for the Fused Location Provider
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleMap mMap;
    private boolean mLocationPermissionGranted = false;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private Location mLastKnownLocation;
    //Keys for storing state
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
public Button addressButton;
    private static final float DEFAULT_ZOOM = (float) 13.3;//Default zoom could be one of several numbers, guess work
    // private final int x= ThreadLocalRandom.current().nextInt(0,5);//Create a number in sequence between 0-5 for the array

    private EditText whereTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
     if(savedInstanceState!=null) {
       mLastKnownLocation=savedInstanceState.getParcelable(KEY_LOCATION);
         CameraPosition cameraPosition=savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
     }
     mPlaceDetectionClient= Places.getPlaceDetectionClient(this);
addressButton=findViewById(R.id.addressButton);


        /**
         * Obtain the SupportMapFragment and get notified when the map is ready to be used.
         *
         */
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
               .findFragmentById(R.id.map);
      mapFragment.getMapAsync(this);


        // updateLocationUI();
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
        mMap=googleMap;
        Toast.makeText(this,"Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
            getLocationPermission();
getDeviceLocation();
updateLocationUI();

        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              passAddress( );
            }
        });
    }

    /**
     * Prompts user for permission to access the device location
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        Log.d(TAG, "Permission Result");
        String[] permissions = {ACCESS_FINE_LOCATION,
                ACCESS_COURSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    ACCESS_COURSE_LOCATION) == PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                      LOCATION_PERMISSION_REQUEST_CODE);}
            } else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
            }
        }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    //initialize our map
                    initMap();
                    getDeviceLocation();
                }
            }
        }

    }
public void initMap(){
       SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
       mapFragment.getMapAsync(MapsActivity.this);

                    }



      private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setAllGesturesEnabled(true);
mMap.setBuildingsEnabled(true);
mMap.setTrafficEnabled(true);
mMap.setIndoorEnabled(true);

            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Gets current location of device, and postions map's camera
     */
    static String city, state, address, postalCode;


    /**
     * Hopefully a better get device location
     *
     */
    private void getDeviceLocation(){
            Log.d(TAG,"get device location currently");
            mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
            try{
                if(mLocationPermissionGranted){
                  @SuppressLint("MissingPermission") final Task location = mFusedLocationProviderClient.getLastLocation();
                    location.addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"onComplete: found Location");
                   mLastKnownLocation= (Location) task.getResult();
                        moveCamera(new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude()),DEFAULT_ZOOM);

                            Geocoder geocoder;
                            List<Address> addresses;
                            geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());

                            try {
                                addresses = geocoder.getFromLocation(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());

                                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                city = addresses.get(0).getLocality();
                                state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                postalCode = addresses.get(0).getPostalCode();
                                String knownName = addresses.get(0).getFeatureName(); // Only
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(latLng);
                                markerOptions.title(address);
                                mMap.clear();//Not sure
                                mMap.addMarker(markerOptions);
                                Marker here = mMap.addMarker(markerOptions.title(address + ", " + city + ", " + state + ", " + country
                                        + ", " + postalCode + ", " + knownName));
                                here.showInfoWindow();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }  }else {
                            Log.d(TAG, "onComplete:  current location is null");
                            Toast.makeText(MapsActivity.this, "unable to get current location",Toast.LENGTH_SHORT).show();
                            getLocationPermission();
                        }
                        }
                    });

                }
            }catch (SecurityException e){
                Log.e(TAG,"getDeviceLocation: SecurityException: " + e.getMessage() );
            }



    }



    /**
     * Method to move the camera
     * @param latLng
     * @param zoom
     */
        private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG,"moveCamera: Moving camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
        }

    /**
     * Pass the Address from the Marker and Location from last known location...
     *
     *
     *
     */
 public void passAddress( ){

        Intent   intent = new Intent(MapsActivity.this,ShoppingActivity.class);
            String address2=address;
            double latitude = mLastKnownLocation.getLatitude();
            double longitude = mLastKnownLocation.getLongitude();

               intent.putExtra("Address", address2);
               intent.putExtra("Latitude", latitude);
               intent.putExtra("Longitude", longitude);
               startActivity(intent);
           }


    }


