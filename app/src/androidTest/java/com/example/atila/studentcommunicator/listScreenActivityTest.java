package com.example.atila.studentcommunicator;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.TextView;

import com.example.atila.studentcommunicator.com.example.atila.studentcommunicator.activities.listScreenActivity;

/**
 * Created by Atila on 04-05-2015.
 */
public class listScreenActivityTest extends ActivityUnitTestCase<listScreenActivity>{

    private Intent mLaunchIntent;
    private listScreenActivity activity;

    public listScreenActivityTest() {
        super(listScreenActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), listScreenActivity.class);
        startActivity(mLaunchIntent, null, null);
        activity = getActivity();
    }

    @SmallTest
    public void testLayoutExists() {
        // Verifies that the views exists
        assertNotNull(activity.findViewById(R.id.textView));
        // Verifies the text of the views
        TextView header = (TextView) activity.findViewById(R.id.textView);
        assertEquals("Incorrect text on header textview", " People nearby you:", header.getText());
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
