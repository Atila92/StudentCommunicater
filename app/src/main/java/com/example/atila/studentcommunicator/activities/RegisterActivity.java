package com.example.atila.studentcommunicator.activities;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.atila.studentcommunicator.net.JSONParser;
import com.example.atila.studentcommunicator.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends Activity implements OnClickListener {


    private static final String URL = "http://toiletgamez.com/bachelor_db/register.php";


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mFullnameView; //tester

    private View mProgressView;
    private View mLoginFormView;
    private Button mEmailSignInButton;
    // Progress Dialog
    private ProgressDialog pDialog;

private static final String TAG_SUCCESS = "success";
private static final String TAG_MESSAGE = "message";

    JSONParser jsonParser = new JSONParser();

@Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    // Set up the login form.
    mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
    mPasswordView = (EditText) findViewById(R.id.password);
    mFullnameView = (EditText) findViewById(R.id.fullName); //test
    mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
    mEmailSignInButton.setOnClickListener(this);


}

            @Override
            public void onClick(View view) {
                new createUser().execute();
            }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
     class createUser extends AsyncTask<String, String,String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO: attempt authentication against a network service.

            int success;
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();
            String name = mFullnameView.getText().toString();
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", email));
                params.add(new BasicNameValuePair("password", password));
                params.add(new BasicNameValuePair("name",name));

                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        URL, "POST", params);

                // full json response
                Log.d("Login attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User Created!", json.toString());
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    finish();
                    startActivity(i);
                    return json.getString(TAG_MESSAGE);
                }else{
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }
        @Override
        protected void onPostExecute(String file_url) {

            //dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(RegisterActivity.this, file_url, Toast.LENGTH_LONG).show();

        }


    }
}}




