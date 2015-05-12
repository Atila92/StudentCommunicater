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
import com.example.atila.studentcommunicator.com.example.atila.studentcommunicator.activities.MenuScreenActivity;

/**
 * Created by Atila on 25-04-2015.
 */
public class MenuScreenActivityFunctionalTest extends ActivityInstrumentationTestCase2<MenuScreenActivity> {

    private MenuScreenActivity activity;

    public MenuScreenActivityFunctionalTest() {
        super(MenuScreenActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        activity = getActivity();
    }

    @MediumTest
    public void testStartSecondActivity() throws Exception {
        // Add monitor to check for the second activity
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(
                LoginActivity.class.getName(), null, false);

        // find button and click it
        Button signOutButton = (Button) activity.findViewById(R.id.signOutButton);
        TouchUtils.clickView(this, signOutButton);

        // Wait 2 seconds for the start of the activity
        LoginActivity secondActivity = (LoginActivity) monitor
                .waitForActivityWithTimeout(2000);
        assertNotNull(secondActivity);

        // Search for the Views
        Button email_sign_in_button = (Button) secondActivity
                .findViewById(R.id.email_sign_in_button);

        AutoCompleteTextView email = (AutoCompleteTextView) secondActivity
                .findViewById(R.id.email);

        EditText password = (EditText) secondActivity
                .findViewById(R.id.password);

        // check that the views is on the screen
        ViewAsserts.assertOnScreen(secondActivity.getWindow().getDecorView(),
                email_sign_in_button);

        ViewAsserts.assertOnScreen(secondActivity.getWindow().getDecorView(),
                email);

        ViewAsserts.assertOnScreen(secondActivity.getWindow().getDecorView(),
                password);

        // Validate the text on the Button and hint in text fields
        assertEquals("Incorrect label of the button", "Sign in", email_sign_in_button.getText());
        assertEquals("Incorrect hint of email textview", "Email", email.getHint());
        assertEquals("Incorrect hint of password textview", "Password", password.getHint());

        // Press back to return to original activity
        // We have to manage the initial position within the emulator
        this.sendKeys(KeyEvent.KEYCODE_BACK);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
