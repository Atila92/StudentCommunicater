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
    private boolean running = true;

    @Override
    public void onCreate() {
        Log.i(TAG, "Updater service startet!");
        Log.i(TAG, "email -------> "+LoginActivity.loginEmail);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(final Location location) {

                Runnable runnable = new Runnable() {

                    @Override
                    public void run() {
                        while (running == true) {
                            Log.i(TAG, "email22222 -------> " + LoginActivity.loginEmail);

                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("latitude", String.valueOf(location.getLatitude())));
                            params.add(new BasicNameValuePair("longitude", String.valueOf(location.getLongitude())));
                            Log.i(TAG, "loooong -------> " + String.valueOf(location.getLongitude()));
                            params.add(new BasicNameValuePair("email", LoginActivity.loginEmail));
                            JSONObject json = jsonParser.makeHttpRequest(
                                    URL, "POST", params);
                            Log.i(TAG, "respons -------> " + json);
                        }
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
    @Override
    public void onDestroy()
    {
        running = false;
        super.onDestroy();
    }
}