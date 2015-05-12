package com.example.atila.studentcommunicator;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;

import com.example.atila.studentcommunicator.com.example.atila.studentcommunicator.activities.MenuScreenActivity;

/**
 * Created by Atila on 22-04-2015.
 */
public class MenuScreenActivityTest extends ActivityUnitTestCase<MenuScreenActivity> {

    private Intent mLaunchIntent;
    private MenuScreenActivity activity;

    public MenuScreenActivityTest() {
        super(MenuScreenActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), MenuScreenActivity.class);
        startActivity(mLaunchIntent, null, null);
        activity = getActivity();
    }

    @SmallTest
    public void testLayoutExists() {
        // Verifies the button exist
        assertNotNull(activity.findViewById(R.id.forumButton));
        assertNotNull(activity.findViewById(R.id.mapButton));
        assertNotNull(activity.findViewById(R.id.listButton));
        // Verifies the text of the button
        Button view = (Button) activity.findViewById(R.id.forumButton);
        assertEquals("Incorrect label of the button", "Course Forums", view.getText());
        Button view2 = (Button) activity.findViewById(R.id.mapButton);
        assertEquals("Incorrect label of the button", "Map", view2.getText());
        Button view3 = (Button) activity.findViewById(R.id.listButton);
        assertEquals("Incorrect label of the button", "Who is nearby?", view3.getText());
    }

    @SmallTest
    public void testIntentTriggerViaOnClick(){
        // Verify button exists on screen
        Button forumButton = (Button) activity.findViewById(R.id.forumButton);
        assertNotNull("Button should not be null", forumButton);
        // Trigger a click on the button
        forumButton.performClick();
        // Verify the intent was started
        Intent triggeredIntent = getStartedActivityIntent();
        assertNotNull("Intent should have triggered after button press",
                triggeredIntent);

        // Verify button exists on screen
        Button mapButton = (Button) activity.findViewById(R.id.mapButton);
        assertNotNull("Button should not be null", mapButton);
        // Trigger a click on the button
        mapButton.performClick();
        // Verify the intent was started
        Intent triggeredIntent2 = getStartedActivityIntent();
        assertNotNull("Intent should have triggered after button press",
                triggeredIntent2);

        // Verify button exists on screen
        Button listButton = (Button) activity.findViewById(R.id.listButton);
        assertNotNull("Button should not be null", listButton);
        // Trigger a click on the button
        listButton.performClick();
        // Verify the intent was started
        Intent triggeredIntent3 = getStartedActivityIntent();
        assertNotNull("Intent should have triggered after button press",
                triggeredIntent3);

    }
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
