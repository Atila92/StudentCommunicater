package com.example.atila.studentcommunicator;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * Created by Atila on 04-05-2015.
 */
public class CourseListActivityTest extends ActivityUnitTestCase<CourseListActivity> {

    private Intent mLaunchIntent;
    private CourseListActivity activity;

    public CourseListActivityTest() {
        super(CourseListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), CourseListActivity.class);
        startActivity(mLaunchIntent, null, null);
        activity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
