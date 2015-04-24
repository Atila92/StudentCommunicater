package com.example.atila.studentcommunicator;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class listScreenActivity extends ActionBarActivity {
    private static final String URL = "http://toiletgamez.com/bachelor_db/display.php";
    private TextView list;

    ArrayList<HashMap<String, String>> arraylist;
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

        list = (TextView) findViewById(R.id.textView);

        Log.v("iListScreen", "onCreate");

        //   ArrayList<String> user = new ArrayList<String>();


        //listen skal smides ind i denne her "list" det er alm. textview.

    }
    @Override
    protected void onResume() {
        super.onResume();
        new listScreen().execute();
    }

        public  class listScreen extends AsyncTask<String, String, String> {


            protected String doInBackground(String... args) {
            Log.d("DoInBackground ","kage ");
                //Instantiate the arraylist to contain all the JSON data.

                arraylist = new ArrayList<HashMap<String, String>>();

                //J parser
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = jsonParser.getJSONFromUrl(URL);


                try {

                        users = jsonObject.getJSONArray(TAG_USER);

                    // looping through all posts according to the json object returned
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject c = users.getJSONObject(i);
                        //gets the content of each tag
                        String name = c.getString("name");
                        String longitude = c.getString("longitude");
                        String  latitude = c.getString("latitude");

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(TAG_NAME, name);
                        map.put(TAG_LON, longitude);
                        map.put(TAG_LAT, latitude);

                        // adding HashList to ArrayList
                        arraylist.add(map);

                        //System.out.println(map);
                        Log.v("listScreen","arraylist:" + arraylist);
                       // list.setText((CharSequence) arraylist);

            //CHANGE TIL ATILA
                    }


                 //       JSONArray cookie = jsonObjet.getJSONArray("[user]");
                  //   String name = (String) jsonObjet.get("name");
                   //   String longitude = (String) jsonObjet.get("longitude");
                    //  String  latitude = (String) jsonObjet.get("latitude");

                    //   user.add(name);
                    //   user.add(longitude);
                    //   user.add(latitude);
                    //  list.setText(user);

                //           list.setText(name);
                 //           list.setText(longitude);
                   //         list.setText(latitude);
                    // Log.d("VIRKER IKKE", "msg");
                //   list.setText((CharSequence) cookie);

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

