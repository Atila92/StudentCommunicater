package com.example.atila.studentcommunicator.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atila.studentcommunicator.models.Course;
import com.example.atila.studentcommunicator.net.JSONParser;
import com.example.atila.studentcommunicator.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CourseListActivity extends ActionBarActivity {

    private static final String TAG = "com.example.atila.studentcommunicator";
    private static final String URL = "http://toiletgamez.com/bachelor_db/show_course_user.php";
    private JSONArray courses = null;
    public ArrayList<String> courseList = new ArrayList<String>();

    //JSON IDS:
    private static final String TAG_COURSE = "course";
    public static String clickedCourseName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        new List().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public  class List extends AsyncTask<String, String, String> {

        protected String doInBackground(String... args) {
            Log.d("DoInBackground ", "Den kaldes!");
            //J parser
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = jsonParser.getJSONFromUrl(URL);

            try {
                courses = jsonObject.getJSONArray(TAG_COURSE);
                // looping through all coordinates according to the json object returned
                for (int i = 0; i < courses.length(); i++) {
                    JSONObject c = courses.getJSONObject(i);
                    //gets the content of each tag
                    String courseId =c.getString("course_id");
                    String courseName = c.getString("course_name");
                    String email = c.getString("email");

                    Course course = new Course(courseName, Integer.parseInt(courseId));
                    if(email.equals(LoginActivity.prefs.getString("email",""))) {
                        courseList.add(course.getName());
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
            listView = (ListView) findViewById(R.id.listView2);
            arrayAdapter = new ArrayAdapter<String>(CourseListActivity.this,R.layout.simplerow, courseList);
            listView.setAdapter(arrayAdapter);
            registerClickCallback();
        }
        private void registerClickCallback(){
            ListView listView;
            listView = (ListView) findViewById(R.id.listView2);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                    TextView textView = (TextView) viewClicked;
                    String message = "nr"+position+ " which is: "+ textView.getText().toString();
                    Toast.makeText(CourseListActivity.this, message, Toast.LENGTH_LONG).show();
                    clickedCourseName =textView.getText().toString();
                    Intent i = new Intent(CourseListActivity.this, ForumActivity.class);
                    startActivity(i);
                }
            });
        }
    }
}
