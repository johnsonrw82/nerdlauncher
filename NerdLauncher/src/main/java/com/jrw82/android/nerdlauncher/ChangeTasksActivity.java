package com.jrw82.android.nerdlauncher;

import android.support.v4.app.Fragment;
import com.jrw82.android.lib.SingleFragmentActivity;

/**
 * Created by johnsonrw82 on 5/15/2015.
 */
public class ChangeTasksActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ChangeTasksFragment();
    }
}
