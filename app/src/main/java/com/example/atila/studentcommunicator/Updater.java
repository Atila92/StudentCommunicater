package com.example.atila.studentcommunicator;

import android.app.IntentService;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class Updater extends IntentService {

    private static final String TAG = "com.example.atila.studentcommunicator";
    private Timer timer = new Timer();
    private TimerTask timerTask;

    public Updater() {
        super("Updater");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Updater service startet!");

        timerTask = new TimerTask(){
        @Override
        public void run(){
            //Get location
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                // Getting latitude of the current location
                double mylatitude = location.getLatitude();

                // Getting longitude of the current location
                double mylongitude = location.getLongitude();
                String latitude = String.valueOf(mylatitude);
                Log.i(TAG, "kig her"+latitude);
            }

        }
        };
        timer.schedule(timerTask, 0, 10000);

    }
}