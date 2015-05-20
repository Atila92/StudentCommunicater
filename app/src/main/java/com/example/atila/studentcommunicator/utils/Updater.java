package com.example.atila.studentcommunicator.utils;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.atila.studentcommunicator.activities.MenuScreenActivity;
import com.example.atila.studentcommunicator.net.JSONParser;
import com.example.atila.studentcommunicator.activities.LoginActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Updater extends Service {

    private static final String TAG = "com.example.atila.studentcommunicator";

    private static final String URL = "http://toiletgamez.com/bachelor_db/updater.php";
    private static final String URL2 = "http://toiletgamez.com/bachelor_db/status.php";
    public static boolean toast = false;
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
    private String email;
    JSONParser jsonParser = new JSONParser();
    private boolean running = true;

    @Override
    public void onCreate() {
        Log.i(TAG, "Updater service startet!");
        email = LoginActivity.prefs.getString("email","");
        //Gives you a location manager
        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //Getting GPS status
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //Creates a location listener
        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(final Location location) {

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                            while(running ==true) {
                                List<NameValuePair> params = new ArrayList<NameValuePair>();
                                params.add(new BasicNameValuePair("latitude", String.valueOf(location.getLatitude())));
                                params.add(new BasicNameValuePair("longitude", String.valueOf(location.getLongitude())));
                                Log.i(TAG, "looong"+ String.valueOf(location.getLongitude()));
                                params.add(new BasicNameValuePair("email", email));
                                JSONObject json = jsonParser.makeHttpRequest(
                                        URL, "POST", params);

                                java.util.List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                                params2.add(new BasicNameValuePair("status", "1"));
                                params2.add(new BasicNameValuePair("email", email));
                                JSONObject json2 = jsonParser.makeHttpRequest(
                                        URL2, "POST", params2);
                            }
                    }
                };
                new Thread(runnable).start();
                if(running == false){
                    locationManager.removeUpdates(this);
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        if(isGPSEnabled) {
            Log.i(TAG, "gps is enabled");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locationListener);
            Log.i(TAG, "getting network provider");
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, locationListener);
            toast = false;
        }else{
            toast = true;
        }
    }
    @Override
    public void onDestroy()
    {
        running = false;
        super.onDestroy();
    }
}