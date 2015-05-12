package com.example.atila.studentcommunicator.com.example.atila.studentcommunicator.activities;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.atila.studentcommunicator.com.example.atila.studentcommunicator.net.JSONParser;
import com.example.atila.studentcommunicator.R;
import com.example.atila.studentcommunicator.com.example.atila.studentcommunicator.utils.Updater;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MenuScreenActivity extends Activity implements OnClickListener {
    private static final String TAG = "com.example.atila.studentcommunicator";
    private static final String URL = "http://toiletgamez.com/bachelor_db/status.php";

    JSONParser jsonParser = new JSONParser();
    //UI References
    private Button mapButton;
    private Button forumButton;
    private Button listButton;
    private Button signOutButton;
    private ImageView settings;
    private Switch locationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final Service UpdaterService = new Updater();

        mapButton = (Button) findViewById(R.id.mapButton);
        forumButton = (Button) findViewById(R.id.forumButton);
        listButton = (Button) findViewById(R.id.listButton);
        signOutButton = (Button) findViewById(R.id.signOutButton);
        settings = (ImageView) findViewById(R.id.imageView2);
        locationSwitch = (Switch) findViewById(R.id.switch1);

        //Setup listeners
        mapButton.setOnClickListener(this);
        forumButton.setOnClickListener(this);
        listButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);
        settings.setOnClickListener(this);
        locationSwitch.setChecked(false);
        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    startService(new Intent(getApplicationContext(), Updater.class));
                }
                else {
                    stopService(new Intent(getApplicationContext(), Updater.class));
                    // Execute some code after 2 seconds have passed
                    Handler handler = new Handler();
                    //Runnable runnable2 = new Runnable() {
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("status", "0"));
                            params.add(new BasicNameValuePair("email", LoginActivity.loginEmail));
                            JSONObject json = jsonParser.makeHttpRequest(
                                    URL, "POST", params);
                            Log.i(TAG, "status set -->" );
                        }
                    },2000);
                    //new Thread(runnable2).start();
                }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    //public boolean buttonColor =true;
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mapButton:
                Intent i = new Intent(MenuScreenActivity.this, MapsActivity.class);
                startActivity(i);
                break;

            case R.id.forumButton:
                Intent l = new Intent(MenuScreenActivity.this, CourseListActivity.class);
                startActivity(l);

                break;

            case R.id.listButton:
                Intent j = new Intent(MenuScreenActivity.this, listScreenActivity.class);
                startActivity(j);

                break;

            case R.id.signOutButton:
                Intent k = new Intent(MenuScreenActivity.this, LoginActivity.class);
                startActivity(k);

                break;

            case R.id.imageView2:
                Intent h = new Intent(MenuScreenActivity.this, SettingsActivity.class);
                startActivity(h);

                break;
            default:
                break;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
