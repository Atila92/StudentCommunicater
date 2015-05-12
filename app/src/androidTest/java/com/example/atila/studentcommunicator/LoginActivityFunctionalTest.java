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
import android.widget.Switch;

import com.example.atila.studentcommunicator.com.example.atila.studentcommunicator.activities.LoginActivity;
import com.example.atila.studentcommunicator.com.example.atila.studentcommunicator.activities.MenuScreenActivity;

/**
 * Created by Atila on 25-04-2015.
 */
public class LoginActivityFunctionalTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity activity;

    public LoginActivityFunctionalTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        activity = getActivity();
    }

    @MediumTest
    public void testStartSecondActivity() throws Exception {
        final String emailValue = "a";
        final String passwordValue = "a";

        // Set a value into the text field
        //final EditText etResult = (EditText) activity.findViewById(R.id.etResult);
        final AutoCompleteTextView email = (AutoCompleteTextView) activity.findViewById(R.id.email);
        final EditText password = (EditText) activity.findViewById(R.id.password);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                email.setText(emailValue);
                password.setText(passwordValue);
            }
        });

        // Add monitor to check for the second activity
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(
                MenuScreenActivity.class.getName(), null, false);

        // find button and click it
        Button signIn = (Button) activity.findViewById(R.id.email_sign_in_button);
        TouchUtils.clickView(this, signIn);

        // Wait 2 seconds for the start of the activity
        MenuScreenActivity secondActivity = (MenuScreenActivity) monitor
                .waitForActivityWithTimeout(2000);
        assertNotNull(secondActivity);

        // Search for the Buttons
        Button listButton = (Button) secondActivity
                .findViewById(R.id.listButton);

        Button mapButton = (Button) secondActivity
                .findViewById(R.id.mapButton);

        Button forumButton = (Button) secondActivity
                .findViewById(R.id.forumButton);

        Button signOutButton = (Button) secondActivity
                .findViewById(R.id.signOutButton);

        Switch switch1 = (Switch) secondActivity
                .findViewById(R.id.switch1);

        // check that the buttons is on the screen
        ViewAsserts.assertOnScreen(secondActivity.getWindow().getDecorView(),
                listButton);

        ViewAsserts.assertOnScreen(secondActivity.getWindow().getDecorView(),
                mapButton);

        ViewAsserts.assertOnScreen(secondActivity.getWindow().getDecorView(),
                forumButton);

        ViewAsserts.assertOnScreen(secondActivity.getWindow().getDecorView(),
                signOutButton);

        ViewAsserts.assertOnScreen(secondActivity.getWindow().getDecorView(),
                switch1);

        // Validate the text on the Button
        assertEquals("Incorrect label of the button", "Who is nearby?", listButton.getText());

        // Press back to return to original activity
        // We have to manage the initial position within the emulator
        this.sendKeys(KeyEvent.KEYCODE_BACK);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
