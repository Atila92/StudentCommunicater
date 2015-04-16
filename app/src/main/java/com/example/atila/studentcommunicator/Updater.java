package com.example.atila.studentcommunicator;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Updater extends Service {

    private static final String TAG = "com.example.atila.studentcommunicator";



    private static final String URL = "http://toiletgamez.com/bachelor_db/updater.php";

    public class LocalBinder extends Binder {
        Updater getService() {
            return Updater.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBinder mBinder = new LocalBinder();

    JSONParser jsonParser = new JSONParser();


    @Override
    public void onCreate() {
        Log.i(TAG, "Updater service startet!");

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //Criteria criteria = new Criteria();
        //String provider = locationManager.getBestProvider(criteria, true);
        //Location location = locationManager.getLastKnownLocation(provider);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(final Location location) {
                // Called when a new location is found by the network location provider.
                // Getting latitude of the current location
                //double mylatitude = location.getLatitude();

                // Getting longitude of the current location
                //double mylongitude = location.getLongitude();
                Log.i(TAG, "location"+location);
                Log.i(TAG, "latitude"+location.getLatitude());
                Log.i(TAG, "longitude"+location.getLongitude());
                //location.
                //if(location!= null) {
                //lat = String.valueOf(location.getLatitude());
                //lon = String.valueOf(location.getLongitude());

                Runnable runnable = new Runnable() {

                    @Override
                    public void run() {
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("latitude", String.valueOf(location.getLatitude())));
                        params.add(new BasicNameValuePair("longitude", String.valueOf(location.getLongitude())));

                        JSONObject json = jsonParser.makeHttpRequest(
                                URL, "POST", params);
                    }
                };
                new Thread(runnable).start();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i(TAG, "bliver kaldt");
            }

            public void onProviderEnabled(String provider) {
                Log.i(TAG, "bliver kaldt2");
            }

            public void onProviderDisabled(String provider) {
                Log.i(TAG, "bliver kaldt3");
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locationListener);


    }
}