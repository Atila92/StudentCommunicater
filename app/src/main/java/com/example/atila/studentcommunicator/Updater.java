package com.example.atila.studentcommunicator;

import android.app.IntentService;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
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
    public float lat;
    public float lon;

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
                lat = (float)mylatitude;
                lon = (float) mylongitude;
                String hej = String.valueOf(lat);
                String farvel = String.valueOf(lon);

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("latitude", hej));
                params.add(new BasicNameValuePair("longtitude", farvel));

                JSONObject json = jsonParser.makeHttpRequest(
                        URL, "POST", params);
            }


            // gem til databsen her hver 10 sec


         //   JSONObject json = JSONParser.makeHttpRequest(URL)




        }
        };
        timer.schedule(timerTask, 0, 10000);

    }
}