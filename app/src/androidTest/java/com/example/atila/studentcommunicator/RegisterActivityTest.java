package com.example.atila.studentcommunicator;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Atila on 23-04-2015.
 */
public class RegisterActivityTest extends ActivityUnitTestCase<RegisterActivity> {

    private Intent mLaunchIntent;
    private RegisterActivity activity;

    public RegisterActivityTest() {
        super(RegisterActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), RegisterActivity.class);
        startActivity(mLaunchIntent, null, null);
        activity = getActivity();
    }

    @SmallTest
    public void testLayoutExists() {
        // Verifies that the views exists
        assertNotNull(activity.findViewById(R.id.fullName));
        assertNotNull(activity.findViewById(R.id.email));
        assertNotNull(activity.findViewById(R.id.password));
        assertNotNull(activity.findViewById(R.id.email_sign_in_button));
        // Verifies the text of the views
        AutoCompleteTextView view1 = (AutoCompleteTextView) activity.findViewById(R.id.fullName);
        assertEquals("Incorrect hint of name textview", "Full Name", view1.getHint());
        AutoCompleteTextView view2 = (AutoCompleteTextView) activity.findViewById(R.id.email);
        assertEquals("Incorrect hint of email textview", "Email", view2.getHint());
        EditText view3 = (EditText) activity.findViewById(R.id.password);
        assertEquals("Incorrect hint of password textview", "Password", view3.getHint());
        Button view4 = (Button) activity.findViewById(R.id.email_sign_in_button);
        assertEquals("Incorrect label on register textview", "Register", view4.getText());
    }

    @SmallTest
    public void testIntentTriggerViaOnClick(){

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
