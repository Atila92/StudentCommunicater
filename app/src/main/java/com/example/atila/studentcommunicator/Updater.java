package com.example.atila.studentcommunicator;

import android.app.IntentService;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Updater extends IntentService {

    private static final String TAG = "com.example.atila.studentcommunicator";
    private Timer timer = new Timer();
    private TimerTask timerTask;
    public String lat;
    public String lon;


    private static final String URL = "http://toiletgamez.com/bachelor_db/updater.php";


    public Updater() {
        super("Updater");
    }

    JSONParser jsonParser = new JSONParser();


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Updater service startet!");

        timerTask = new TimerTask(){
        @Override
        public void run() {

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);


            //Location location = locationManager.getLastKnownLocation(provider);
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.
                    // Getting latitude of the current location
                    double mylatitude = location.getLatitude();

                    // Getting longitude of the current location
                    double mylongitude = location.getLongitude();
                    if(location!= null) {
                        lat = String.valueOf(mylatitude);
                        lon = String.valueOf(mylongitude);
                    }
                }
                public void onStatusChanged(String provider, int status, Bundle extras) {}

                public void onProviderEnabled(String provider) {}

                public void onProviderDisabled(String provider) {}
            };
            locationManager.requestLocationUpdates(provider, 10000, 0, locationListener);

            //if (location != null) {

                //lat = String.valueOf(mylatitude);
                //lon = String.valueOf(mylongitude);


                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("latitude", lat));
                params.add(new BasicNameValuePair("longitude", lon));

                JSONObject json = jsonParser.makeHttpRequest(
                        URL, "POST", params);

                Log.i(TAG, "Den når ned!" + lat + "  " + lon);
                // gem til databsen her hver 10 sec


                //   JSONObject json = JSONParser.makeHttpRequest(URL)

            }
       // }
        };
        timer.schedule(timerTask, 0, 10000);

    }

}