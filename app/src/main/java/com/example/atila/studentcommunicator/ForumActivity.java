package com.example.atila.studentcommunicator;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ForumActivity extends ActionBarActivity implements OnClickListener {

    private static final String TAG = "com.example.atila.studentcommunicator";
    private static final String URL1 = "http://toiletgamez.com/bachelor_db/get_post.php";
    //private static final String URL2 = "http://toiletgamez.com/bachelor_db/get_post.php";
    private static final String URL3 = "http://toiletgamez.com/bachelor_db/save_post.php";
    private JSONArray posts = null;
    public ArrayList<String> postList = new ArrayList<String>();
    public ArrayList<String> newPost = new ArrayList<String>();
    JSONParser jsonParser = new JSONParser();

    //JSON IDS:
    private static final String TAG_POST = "post";

    private TextView courseTitle;
    private EditText inputField;
    private Button postButton;
    public String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        courseTitle = (TextView) findViewById(R.id.courseTitle);
        courseTitle.setText(CourseListActivity.clickedCourseName);
        inputField = (EditText) findViewById(R.id.editText);
        postButton = (Button) findViewById(R.id.postButton);

        postButton.setOnClickListener(this);
        new PostList().execute();

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        Runnable runnable2 = new Runnable() {

            @Override
            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("course_name", CourseListActivity.clickedCourseName));
                params.add(new BasicNameValuePair("name","??"));
                //Log.i("login activity", "email heeeeeeer: " + LoginActivity.loginEmail);
                params.add(new BasicNameValuePair("message", message));
                JSONObject json = jsonParser.makeHttpRequest(
                        URL3, "POST", params);
            }
        };
        message = inputField.getText().toString();
        new Thread(runnable2).start();
        inputField.setText("");
        inputField.clearFocus();
        Toast.makeText(ForumActivity.this, "Posted!", Toast.LENGTH_LONG).show();
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

                    Post post = new Post(Integer.parseInt(postId), courseName, name, message);
                    Log.i("clicked course name ", CourseListActivity.clickedCourseName+ " : "+ post.getCourseName());
                    if(post.getCourseName().equals(CourseListActivity.clickedCourseName)) {
                        postList.add(post.getName() + " : " + post.getMessage());
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
            arrayAdapter = new ArrayAdapter<String>(ForumActivity.this, R.layout.simplerow, postList);
            listView.setAdapter(arrayAdapter);
        }

    }
}
