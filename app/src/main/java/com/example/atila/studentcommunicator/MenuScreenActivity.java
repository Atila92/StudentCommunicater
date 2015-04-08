package com.example.atila.studentcommunicator;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MenuScreenActivity extends ActionBarActivity implements OnClickListener {

    //UI References
    private Button activateButton;
    private Button mapButton;
    private Button forumButton;
    private Button signOutButton;
    private ImageView settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        activateButton = (Button) findViewById(R.id.activateButton);
        mapButton = (Button) findViewById(R.id.mapButton);
        forumButton = (Button) findViewById(R.id.forumButton);
        signOutButton = (Button) findViewById(R.id.signOutButton);
        settings = (ImageView) findViewById(R.id.imageView2);

        //Setup listeners
        activateButton.setOnClickListener(this);
        mapButton.setOnClickListener(this);
        forumButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);
        settings.setOnClickListener(this);
    }
    public boolean buttonColor =true;
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activateButton:
                if(buttonColor == true) {
                    activateButton.setBackgroundColor(Color.GREEN);
                    activateButton.setText("Deactivate Button");
                    buttonColor = false;
                }
                else if(buttonColor == false) {
                    activateButton.setBackgroundColor(Color.RED);
                    activateButton.setText("Activate Button");
                    buttonColor = true;
                }

                break;

            case R.id.mapButton:
                Intent i = new Intent(MenuScreenActivity.this, MapsActivity.class);
                startActivity(i);
                break;

            case R.id.forumButton:
                Intent l = new Intent(MenuScreenActivity.this, ForumActivity.class);
                startActivity(l);

                break;

            case R.id.signOutButton:
                Intent k = new Intent(MenuScreenActivity.this, LoginActivity.class);
                startActivity(k);

                break;

            case R.id.imageView2:
                Intent j = new Intent(MenuScreenActivity.this, SettingsActivity.class);
                startActivity(j);

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
