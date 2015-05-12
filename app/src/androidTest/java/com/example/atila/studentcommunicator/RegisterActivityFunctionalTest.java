package com.example.atila.studentcommunicator;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.KeyEvent;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.atila.studentcommunicator.com.example.atila.studentcommunicator.activities.LoginActivity;
import com.example.atila.studentcommunicator.com.example.atila.studentcommunicator.activities.RegisterActivity;
import com.example.atila.studentcommunicator.net.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Atila on 25-04-2015.
 */
public class RegisterActivityFunctionalTest extends ActivityInstrumentationTestCase2<RegisterActivity> {

    private static final String URL = "http://toiletgamez.com/bachelor_db/delete.php";

    public String emailRegistered = "test12@student.sdu.dk";
    private RegisterActivity activity;

    public RegisterActivityFunctionalTest() {
        super(RegisterActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        activity = getActivity();
    }

    @MediumTest
    public void testStartSecondActivity() throws Exception {
        final String nameValue = "tester";
        final String emailValue = "test12@student.sdu.dk";
        final String passwordValue = "12345";

        // Set a value into the text field
        //final EditText etResult = (EditText) activity.findViewById(R.id.etResult);
        final AutoCompleteTextView fullName = (AutoCompleteTextView) activity.findViewById(R.id.fullName);
        final AutoCompleteTextView email = (AutoCompleteTextView) activity.findViewById(R.id.email);
        final EditText password = (EditText) activity.findViewById(R.id.password);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fullName.setText(nameValue);
                email.setText(emailValue);
                password.setText(passwordValue);
            }
        });

        // Add monitor to check for the second activity
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(
                LoginActivity.class.getName(), null, false);

        // find button and click it
        Button signInButton = (Button) activity.findViewById(R.id.email_sign_in_button);
        TouchUtils.clickView(this, signInButton);

        // Wait 3 seconds for register and start of the activity
        LoginActivity secondActivity = (LoginActivity) monitor
                .waitForActivityWithTimeout(3000);
        assertNotNull(secondActivity);

        // Search for the Views
        Button email_sign_in_button = (Button) secondActivity
                .findViewById(R.id.email_sign_in_button);

        AutoCompleteTextView logInEmail = (AutoCompleteTextView) secondActivity
                .findViewById(R.id.email);

        EditText logInPassword = (EditText) secondActivity
                .findViewById(R.id.password);

        // check that the views is on the screen
        ViewAsserts.assertOnScreen(secondActivity.getWindow().getDecorView(),
                email_sign_in_button);

        ViewAsserts.assertOnScreen(secondActivity.getWindow().getDecorView(),
               logInEmail);

        ViewAsserts.assertOnScreen(secondActivity.getWindow().getDecorView(),
                logInPassword);

        // Validate the text on the Button and hint in text fields
        assertEquals("Incorrect label of the button", "Sign in", email_sign_in_button.getText());
        assertEquals("Incorrect hint of email textview", "Email", logInEmail.getHint());
        assertEquals("Incorrect hint of password textview", "Password", logInPassword.getHint());

        // Press back to return to original activity
        // We have to manage the initial position within the emulator
        this.sendKeys(KeyEvent.KEYCODE_BACK);
    }


    @Override
    protected void tearDown() throws Exception {
        //Delete user again
        final JSONParser jsonParser = new JSONParser();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("email", "test12@student.sdu.dk"));
                    JSONObject json = jsonParser.makeHttpRequest(
                            URL, "POST", params);
                }

        };
        new Thread(runnable).start();
        super.tearDown();
    }
}
