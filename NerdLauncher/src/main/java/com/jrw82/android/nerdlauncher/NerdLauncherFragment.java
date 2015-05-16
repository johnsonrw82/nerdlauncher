package com.jrw82.android.nerdlauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by johnsonrw82 on 5/11/2015.
 */
public class NerdLauncherFragment extends ListFragment {
    private static final String TAG = "NerdLauncherFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(i,0);

        Log.i(TAG, "Received " + activities.size() + " activities matching intent category");

        // comparator to sort items in alphabetical order
        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo o1, ResolveInfo o2) {
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(o1.loadLabel(pm).toString(), o2.loadLabel(pm).toString());
            }
        });

        // create a custom adapter that will be used to show the ResolveInfo items in the list
        ArrayAdapter<ResolveInfo> adapter = new ArrayAdapter<ResolveInfo>(getActivity(), android.R.layout.simple_list_item_1, activities) {
            public View getView(int pos, View convertView, ViewGroup parent) {
                PackageManager pm = getActivity().getPackageManager();
                View v = super.getView(pos, convertView, parent);
                // docs say simple_list_item_1 is a TextView, so cast it here
                TextView textView = (TextView)v;
                // get the current ResolveItem from the list
                ResolveInfo ri = getItem(pos);
                // set text to the activity label
                textView.setText(ri.loadLabel(pm));

                // use the drawable property of the text view to display the icon for the activity, without needing a custom layout
                textView.setCompoundDrawablesWithIntrinsicBounds(ri.loadIcon(pm), null, null, null);
                textView.setCompoundDrawablePadding(8);

                return v;
            }
        };

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ResolveInfo resolveInfo = (ResolveInfo) l.getAdapter().getItem(position);
        ActivityInfo activityInfo = resolveInfo.activityInfo;

        if ( activityInfo != null ) {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setClassName(activityInfo.applicationInfo.packageName, activityInfo.name);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(i);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_nerd_launcher, menu);
        MenuItem reorderTasks = menu.findItem(R.id.menu_item_reorderTasks);

        reorderTasks.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // launch dialog
                Intent i = new Intent(getActivity(), ChangeTasksActivity.class);
                startActivity(i);
                return true;
            }
        });
    }

}
