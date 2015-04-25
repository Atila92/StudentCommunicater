package com.example.atila.studentcommunicator;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class listScreenActivity extends ActionBarActivity {
    private static final String TAG = "com.example.atila.studentcommunicator";
    private static final String URL = "http://toiletgamez.com/bachelor_db/display.php";
    private ListView listView;

    public ArrayList<String> list = new ArrayList<String>();
    private JSONArray users = null;

    //JSON IDS:
    private static final String TAG_USER = "user";
    private static final String TAG_NAME = "name";
    private static final String TAG_LON = "longitude";
    private static final String TAG_LAT = "latitude";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_screen);

        listView = (ListView) findViewById(R.id.listView);
        new List().execute();

        //Log.i("listScreen","arraylist:" +list);
        //new List().execute();



        //list.setText(arraylist);

        //   ArrayList<String> user = new ArrayList<String>();


        //listen skal smides ind i denne her "list" det er alm. textview.

    }
    @Override
    protected void onResume() {
        super.onResume();

    }

        public  class List extends AsyncTask<String, String, String> {


            protected String doInBackground(String... args) {
            Log.d("DoInBackground ","Den kaldes!");
                //Instantiate the arraylist to contain all the JSON data.
               // ArrayList<String> list = new ArrayList<String>();
                //ArrayList<String> data = new ArrayList<String>();
                //J parser
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = jsonParser.getJSONFromUrl(URL);

                try {
                        users = jsonObject.getJSONArray(TAG_USER);

                    // looping through all coordinates according to the json object returned
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject c = users.getJSONObject(i);
                        //gets the content of each tag
                        String name = c.getString("name");
                        String longitude = c.getString("longitude");
                        String  latitude = c.getString("latitude");

                        // creating new HashMap
                        //HashMap<String, String> map = new HashMap<String, String>();
                        list.add(name);
                        list.add(latitude);
                        list.add(longitude);
                        //map.put(TAG_NAME, name);
                        //map.put(TAG_LON, longitude);
                        //map.put(TAG_LAT, latitude);

                        //listView.setText(list);

                        // adding HashList to ArrayList
                        //arraylist.add(map);
                    }
                    Log.i("listScreen","arraylist:" +list);
                    //List<String> your_array_list = new ArrayList<String>();
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(listScreenActivity.this, android.R.layout.simple_list_item_1, list);

                    //listView.setAdapter(arrayAdapter);
                    //listView;
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(String file_url) {

                //     showProgress(false);
                // pDialog.dismiss();
                if (file_url != null) {
                    Toast.makeText(listScreenActivity.this, file_url, Toast.LENGTH_LONG).show();

                }


            }
        }
    }

