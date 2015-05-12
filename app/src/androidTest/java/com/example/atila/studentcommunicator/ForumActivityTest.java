package com.example.atila.studentcommunicator;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Atila on 04-05-2015.
 */
public class ForumActivityTest extends ActivityUnitTestCase<ForumActivity> {

    private Intent mLaunchIntent;
    private ForumActivity activity;

    public ForumActivityTest() {
        super(ForumActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), ForumActivity.class);
        startActivity(mLaunchIntent, null, null);
        activity = getActivity();
    }

    @SmallTest
    public void testLayoutExists() {
        // Verifies that the views exists
        assertNotNull(activity.findViewById(R.id.courseTitle));
        assertNotNull(activity.findViewById(R.id.editText));
        assertNotNull(activity.findViewById(R.id.postButton));
        // Verifies the text of the views
        Button postButton = (Button) activity.findViewById(R.id.postButton);
        assertEquals("Incorrect text on post button", "Post", postButton.getText());
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
