package com.vasanthpandiarajan.project3;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.media.VolumeShaper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.ActionBar;

public class MainActivity extends AppCompatActivity implements MonumentListFragment.ListSelectionListener {
    private final MonumentWebFragment monumentsWeb = new MonumentWebFragment();
    private FragmentManager mFragmentManager;
    private FrameLayout monumentListFrameLayout, monumentsWebFrameLayout;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "MainActivity";

    public static String[] monuments_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        monuments_list = getResources().getStringArray(R.array.monuments_list);

        setContentView(R.layout.activity_main);

        Toolbar myToolBar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(myToolBar);

        monumentListFrameLayout = (FrameLayout) findViewById(R.id.monument_list_container);
        monumentsWebFrameLayout = (FrameLayout) findViewById(R.id.monument_web_container);

        mFragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.monument_list_container, new MonumentListFragment());
        Log.i(TAG, "Fragment should be added");

        fragmentTransaction.commit();

        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                setLayout();
            }
        });

    }

    public void setLayout() {
        if(!monumentsWeb.isAdded()) {
            monumentListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            monumentsWebFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
            Log.i(TAG, "Web Fragment Not Added");
        } else {
            monumentListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1f));
            monumentsWebFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 2f));
            Log.i(TAG, "Web Fragment Added");
        }
    }

//    public void onCreateOption() {
//
//    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar, menu);
        //return true;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exiting:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(this, "Exiting app", Toast.LENGTH_LONG).show();
                return true;

            case R.id.openb:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Toast.makeText(this, "Opening Gallery...", Toast.LENGTH_LONG).show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                Toast.makeText(this, "Default Option", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);

        }
    }

    public void onListSelection(int position) {
        if(!monumentsWeb.isAdded()) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.monument_web_container, monumentsWeb);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            mFragmentManager.executePendingTransactions();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Landscape", Toast.LENGTH_SHORT).show();
        } else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "Portrait", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }

}
