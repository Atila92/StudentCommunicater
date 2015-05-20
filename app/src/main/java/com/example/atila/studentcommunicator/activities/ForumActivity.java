package com.example.atila.studentcommunicator.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atila.studentcommunicator.net.JSONParser;
import com.example.atila.studentcommunicator.models.Post;
import com.example.atila.studentcommunicator.R;
import com.example.atila.studentcommunicator.models.user;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class ForumActivity extends Activity implements OnClickListener {

    private static final String TAG = "com.example.atila.studentcommunicator";
    private static final String URL1 = "http://toiletgamez.com/bachelor_db/get_post.php";
    private static final String URL2 = "http://toiletgamez.com/bachelor_db/display2.php";
    private static final String URL3 = "http://toiletgamez.com/bachelor_db/save_post.php";
    private JSONArray posts = null;
    private JSONArray users = null;
    public ArrayList<String> postList = new ArrayList<String>();
    public ArrayList<String> newPost = new ArrayList<String>();
    JSONParser jsonParser = new JSONParser();

    //JSON IDS:
    private static final String TAG_POST = "post";
    private static final String TAG_USER = "user";

    private TextView courseTitle;
    private EditText inputField;
    private Button postButton,fileUploadButton, fileDownloadButton;
    public String message;
    public String userName;
    public String timestamp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        courseTitle = (TextView) findViewById(R.id.courseTitle);
        courseTitle.setText(CourseListActivity.clickedCourseName);
        inputField = (EditText) findViewById(R.id.editText);
        postButton = (Button) findViewById(R.id.postButton);
        fileUploadButton = (Button) findViewById(R.id.fileUploadButton);
        fileDownloadButton = (Button) findViewById(R.id.fileDownloadButton);
        postButton.setOnClickListener(this);
        fileUploadButton.setOnClickListener(this);
        fileDownloadButton.setOnClickListener(this);
        new Name().execute();
        new PostList().execute();

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.postButton:
                DateFormat dateFormatter = new SimpleDateFormat("dd:MM:yyyy");
                dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date today = new Date();
                timestamp = dateFormatter.format(today);
            Runnable runnable2 = new Runnable() {

                @Override
                public void run() {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("course_name", CourseListActivity.clickedCourseName));
                    params.add(new BasicNameValuePair("name", userName));
                    //Log.i("login activity", "email heeeeeeer: " + LoginActivity.loginEmail);
                    params.add(new BasicNameValuePair("message", message));
                    params.add(new BasicNameValuePair("timestamp", timestamp));
                    JSONObject json = jsonParser.makeHttpRequest(
                            URL3, "POST", params);
                }
            };
            message = inputField.getText().toString();
            new Thread(runnable2).start();
            inputField.setText("");
            inputField.clearFocus();
                Log.i("timestamp" ,timestamp);
            //puts the new post into the listview
            newPost.add(userName + " : " + message + "\r\n"+ timestamp);
            final ListView listView;
            final ArrayAdapter arrayAdapter2;
            listView = (ListView) findViewById(R.id.listView4);
            arrayAdapter2 = new ArrayAdapter<String>(ForumActivity.this, R.layout.simplerow_yellow, newPost);
            listView.setAdapter(arrayAdapter2);
            Toast.makeText(ForumActivity.this, "Posted!", Toast.LENGTH_LONG).show();
            //Scrolls the listview down to the bottom
            arrayAdapter2.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    listView.setSelection(arrayAdapter2.getCount() - 1);
                }
            });

            break;

            case R.id.fileUploadButton:
                Uri uri = Uri.parse("http://toiletgamez.com/bachelor_db/save_file.php");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            break;

            case R.id.fileDownloadButton:
                Uri uri2 = Uri.parse("http://toiletgamez.com/bachelor_db/test.php");
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
                startActivity(intent2);
            break;
        }
    }

    public  class PostList extends AsyncTask<String, String, String> {

        protected String doInBackground(String... args) {
            Log.d("DoInBackground ", "Den kaldes!");
            //J parser
           // JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = jsonParser.getJSONFromUrl(URL1);

            try {
                posts = jsonObject.getJSONArray(TAG_POST);
                // looping through all coordinates according to the json object returned
                for (int i = 0; i < posts.length(); i++) {
                    JSONObject c = posts.getJSONObject(i);
                    //gets the content of each tag
                    String postId = c.getString("post_id");
                    String courseName = c.getString("course_name");
                    String name = c.getString("name");
                    String message = c.getString("message");
                    String time = c.getString("timestamp");

                    Post post = new Post(Integer.parseInt(postId), courseName, name, message, time);
                    Log.i("name op ", name);
                    Log.i("post id op ", postId);

                    if(post.getCourseName().equals(CourseListActivity.clickedCourseName)){
                        postList.add(post.getName() + " : " + post.getMessage()+ "\r\n"+ post.getTimestamp());

                    }else{
                        continue;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ListView listView;
            ArrayAdapter arrayAdapter;
            listView = (ListView) findViewById(R.id.listView3);
            arrayAdapter = new ArrayAdapter<String>(ForumActivity.this, R.layout.simplerow_green, postList);
            listView.setAdapter(arrayAdapter);

        }

    }
    public  class Name extends AsyncTask<String, String, String> {

        protected String doInBackground(String... args) {
            Log.d("Name ", "Den kaldes!");
            //J parser
            // JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject2 = jsonParser.getJSONFromUrl(URL2);

            try {
                users = jsonObject2.getJSONArray(TAG_USER);
                // looping through all coordinates according to the json object returned
                for (int i = 0; i < users.length(); i++) {
                    JSONObject c = users.getJSONObject(i);
                    //gets the content of each tag
                    String email = c.getString("email");
                    String name = c.getString("name");
                    String longitude = c.getString("longitude");
                    String  latitude = c.getString("latitude");

                    user user = new com.example.atila.studentcommunicator.models.user(email, name, Double.parseDouble(longitude), Double.parseDouble(latitude));
                    if(user.getEmail().equals(LoginActivity.prefs.getString("email",""))) {
                       userName = user.getName();
                        Log.i("email kaldes også! ", userName);
                    }else{
                        continue;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {

        }

    }
}
