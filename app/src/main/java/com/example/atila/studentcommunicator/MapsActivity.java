package com.example.atila.studentcommunicator;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class MapsActivity extends FragmentActivity implements OnMapClickListener, OnMapLongClickListener, OnMarkerDragListener{

    GoogleMap mMap; // Might be null if Google Play services APK is not available.
    LatLng myPosition;

    final int RQS_GooglePlayServices = 1;
    Location otherLocation;
    boolean markerClicked;
    PolygonOptions polygonOptions;
    Polygon polygon;
    LatLng newPos;
    public float distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap = fm.getMap();
        mMap.setMyLocationEnabled(true);

        //listeners for marker and map
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerDragListener(this);

        markerClicked = false;
        //mMap.getUiSettings().setMyLocationButtonEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();

            //Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            myPosition = latLng;

            mMap.addMarker(new MarkerOptions().position(myPosition).title("Atila"));
            System.out.print("DEN ER HER!");

        }

    }
    @Override
    public void onMapClick(LatLng point) {
        //tvLocInfo.setText(point.toString());

        mMap.animateCamera(CameraUpdateFactory.newLatLng(point));

        markerClicked = false;

    }

    @Override
    public void onMapLongClick(LatLng point) {
       // tvLocInfo.setText("New marker added@" + point.toString());

        mMap.addMarker(new MarkerOptions()
                .position(point)
                .draggable(true).title("Someone"));

        markerClicked = false;

    }

    @Override
    public void onMarkerDrag(Marker marker) {
       // tvLocInfo.setText("Marker " + marker.getId() + " Drag@" + marker.getPosition());
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        double newLatitude = marker.getPosition().latitude;
        double newLongitude = marker.getPosition().longitude;

        LatLng newlatLng = new LatLng(newLatitude, newLongitude);

        newPos = newlatLng;


        float[] results = new float[1];
        Location.distanceBetween(myPosition.latitude, myPosition.longitude,
                newPos.latitude, newPos.longitude, results);
        System.out.println("----------------------->"+results[0]);
        distance = results[0];
        notifyUser();


    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        //tvLocInfo.setText("Marker " + marker.getId() + " DragStart");

    }

    public void notifyUser(){

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("StudentCommunicator")
                        .setContentText("Someone is "+distance +"m from you" );
        int NOTIFICATION_ID = 1;
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
