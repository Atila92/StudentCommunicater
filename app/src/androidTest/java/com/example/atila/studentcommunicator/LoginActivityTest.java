package com.example.atila.studentcommunicator;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;

/**
 * Created by Atila on 23-04-2015.
 */
public class LoginActivityTest extends ActivityUnitTestCase<LoginActivity>{

    private Intent mLaunchIntent;
    private LoginActivity activity;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), LoginActivity.class);
        startActivity(mLaunchIntent, null, null);
        activity = getActivity();
    }

    @SmallTest
    public void testLayoutExists() {
        // Verifies that the views exists
        assertNotNull(activity.findViewById(R.id.email));
        assertNotNull(activity.findViewById(R.id.password));
        assertNotNull(activity.findViewById(R.id.email_sign_in_button));
        assertNotNull(activity.findViewById(R.id.link_to_register));
        assertNotNull(activity.findViewById(R.id.imageView));
        // Verifies the text of the views
        AutoCompleteTextView view1 = (AutoCompleteTextView) activity.findViewById(R.id.email);
        assertEquals("Incorrect hint of email textview", "Email", view1.getHint());
        EditText view2 = (EditText) activity.findViewById(R.id.password);
        assertEquals("Incorrect hint of password textview", "Password", view2.getHint());
        Button view3 = (Button) activity.findViewById(R.id.email_sign_in_button);
        assertEquals("Incorrect label of the button", "Sign in", view3.getText());
        TextView view4 = (TextView) activity.findViewById(R.id.link_to_register);
        assertEquals("Incorrect text on register textview", "New? Register here", view4.getText());
    }

    @SmallTest
    public void testIntentTriggerViaOnClick(){
        // Verify register link exists on screen
        TextView registerLink = (TextView) activity.findViewById(R.id.link_to_register);
        assertNotNull("Register link should not be null", registerLink);
        // Trigger a click on the button
        registerLink.performClick();
        // Verify the intent was started
        Intent triggeredIntent2 = getStartedActivityIntent();
        assertNotNull("Intent should have triggered after button press",
                triggeredIntent2);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
