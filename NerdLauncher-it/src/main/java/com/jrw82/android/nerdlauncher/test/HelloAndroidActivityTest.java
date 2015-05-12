package com.jrw82.android.nerdlauncher.test;

import android.test.ActivityInstrumentationTestCase2;
import com.jrw82.android.nerdlauncher.*;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<HelloAndroidActivity> {

    public HelloAndroidActivityTest() {
        super(HelloAndroidActivity.class); 
    }

    public void testActivity() {
        HelloAndroidActivity activity = getActivity();
        assertNotNull(activity);
    }
}

