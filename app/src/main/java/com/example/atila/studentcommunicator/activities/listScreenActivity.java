package com.example.atila.studentcommunicator.activities;
import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.atila.studentcommunicator.net.JSONParser;
import com.example.atila.studentcommunicator.R;
import com.example.atila.studentcommunicator.models.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class listScreenActivity extends Activity {
    private static final String TAG = "com.example.atila.studentcommunicator";
    private static final String URL = "http://toiletgamez.com/bachelor_db/display.php";
    public ArrayList<user> list = new ArrayList<>();
    private JSONArray users = null;
    public ArrayList<String> nearbyUsers = new ArrayList<String>();
    private TextView title;
    //JSON ID:
    private static final String TAG_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_screen);
        title = (TextView) findViewById(R.id.textView);
        if(MenuScreenActivity.visibility == false){
            title.setText(" Please go back and set visibility on!");
            title.setTextColor(Color.RED);
        }else{
            title.setText(" People nearby you:");
        }
        new List().execute();

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

        public  class List extends AsyncTask<String, String, String> {

            protected String doInBackground(String... args) {

                //J parser
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = jsonParser.getJSONFromUrl(URL);

                try {
                    users = jsonObject.getJSONArray(TAG_USER);
                    user currentUser = null;
                    // looping through all coordinates according to the json object returned
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject c = users.getJSONObject(i);
                        //gets the content of each tag
                        String email =c.getString("email");
                        String name = c.getString("name");
                        String longitude = c.getString("longitude");
                        String  latitude = c.getString("latitude");

                        user user = new com.example.atila.studentcommunicator.models.user(email, name, Double.parseDouble(longitude), Double.parseDouble(latitude));
                        Log.i("hent det op ",email+ " "+ name);
                        if(email.equals(LoginActivity.prefs.getString("email",""))){
                            currentUser = user;
                            Log.i("current user ", currentUser.getName());
                        }
                        list.add(user);
                    }
                    if(currentUser != null){
                        ArrayList<String> names = new ArrayList<String>();
                        float[] results = new float[1];
                        for(com.example.atila.studentcommunicator.models.user user : list){
                            if(currentUser.getEmail() == user.getEmail()){
                                continue;
                            }
                            Log.i("måler location ", "--");
                            Location.distanceBetween(currentUser.getLatitude(), currentUser.getLongitude(),
                                    user.getLatitude(), user.getLongitude(), results);
                            if(results[0] <= 200){
                                names.add(user.getName());
                                Log.i("names listen ",user.getName());
                            }
                        }
                        nearbyUsers = names;

                        Log.i("listScreen","arraylist:" +names);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                Log.i("listScreen","onpostexecute:" +nearbyUsers);
                ListView listView;
                ArrayAdapter arrayAdapter;
                listView = (ListView) findViewById(R.id.listView);
                arrayAdapter = new ArrayAdapter<String>(listScreenActivity.this,R.layout.simplerow, nearbyUsers);
                listView.setAdapter(arrayAdapter);


            }
        }
    }

