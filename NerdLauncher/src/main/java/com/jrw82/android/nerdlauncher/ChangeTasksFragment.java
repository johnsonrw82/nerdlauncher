package com.jrw82.android.nerdlauncher;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by johnsonrw82 on 5/15/2015.
 */
public class ChangeTasksFragment extends ListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle(R.string.title_runningTasks);
        setHasOptionsMenu(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ActivityManager am = (ActivityManager) getActivity().getSystemService(Activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(25);

        setListAdapter(new ArrayAdapter<ActivityManager.RunningTaskInfo>(getActivity(), android.R.layout.simple_list_item_1, tasks) {
            @Override
            public View getView(int pos, View convertView, ViewGroup parent ) {
                View v = super.getView(pos, convertView, parent);

                TextView textView = (TextView)v;
                ActivityManager.RunningTaskInfo rti = getItem(pos);

                textView.setText(rti.baseActivity.toString());

                return v;
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ActivityManager.RunningTaskInfo rti = (ActivityManager.RunningTaskInfo) l.getAdapter().getItem(position);

        ActivityManager am = (ActivityManager) getActivity().getSystemService(Activity.ACTIVITY_SERVICE);
        am.moveTaskToFront(rti.id, ActivityManager.MOVE_TASK_WITH_HOME);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                // if there is an activity identified as the parent, use it to navigate up the stack
                navigateUp();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void navigateUp() {
        if (NavUtils.getParentActivityName(getActivity()) != null ) {
            NavUtils.navigateUpFromSameTask(getActivity());
        }
        else {
            getActivity().finish();
        }
    }
}
