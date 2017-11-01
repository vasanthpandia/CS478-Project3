package com.vasanthpandiarajan.project3;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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


import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements MonumentListFragment.ListSelectionListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private MonumentWebFragment monumentsWeb;
    private MonumentListFragment monumentListFragment;
    private FragmentManager mFragmentManager;
    private FrameLayout monumentListFrameLayout, monumentsWebFrameLayout;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "MainActivity";
    private static final String TAG_RETAINED_LIST_FRAGMENT = "RetainedListFragment";
    private static final String TAG_RETAINED_WEB_FRAGMENT = "RetainedWebFragment";
    private final int REQUEST_PERMISSION_DEADLY_STATE=1;

    int config;

    public static String[] monuments_list, monument_urls_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        monuments_list = getResources().getStringArray(R.array.monuments_list);
        monument_urls_list = getResources().getStringArray(R.array.monument_urls);

        setContentView(R.layout.activity_main);
        config = getResources().getConfiguration().orientation;

        Toolbar myToolBar = (Toolbar) findViewById(R.id.toolbar1); // ActionBar
        setSupportActionBar(myToolBar);

        monumentListFrameLayout = (FrameLayout) findViewById(R.id.monument_list_container);
        monumentsWebFrameLayout = (FrameLayout) findViewById(R.id.monument_web_container);

        FragmentTransaction fragmentTransaction;

        mFragmentManager = getFragmentManager();

        // Find fragment with list of monuments - Add to the view
        if(mFragmentManager.findFragmentByTag(TAG_RETAINED_LIST_FRAGMENT) == null) {
            monumentListFragment = new MonumentListFragment();
            fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.monument_list_container, monumentListFragment, TAG_RETAINED_LIST_FRAGMENT);
            Log.i(TAG, "Fragment should be added");
            fragmentTransaction.commit();
        }

        // Find the retained instance of the fragment that holds the webview - Create new if not present
        if(mFragmentManager.findFragmentByTag(TAG_RETAINED_WEB_FRAGMENT) == null) {
            Log.i(TAG_RETAINED_WEB_FRAGMENT, "NULL");
            monumentsWeb = new MonumentWebFragment();
        } else {
            monumentsWeb = (MonumentWebFragment) mFragmentManager.findFragmentByTag(TAG_RETAINED_WEB_FRAGMENT);
        }

        setLayout();

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
        }
        else {
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                monumentListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
                monumentsWebFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            }
            else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                monumentListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1f));
                monumentsWebFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 2f));
                Log.i(TAG, "Web Fragment Added");
            }
        }
    }

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
                // Exiting is clicked
                return true;

            case R.id.openb:
                // Open Gallery is clicked
                if(ContextCompat.checkSelfPermission(this, "com.vasanthpandiarajan.monumentsgrid.permission.DEADLY_ACTIVITY") == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission already present", Toast.LENGTH_SHORT).show();
                    loadGallery();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{"com.vasanthpandiarajan.monumentsgrid.permission.DEADLY_ACTIVITY"}, REQUEST_PERMISSION_DEADLY_STATE);
                    Toast.makeText(this, "Permission Dialog should open now - " + ContextCompat.checkSelfPermission(this, "com.vasanthpandiarajan.monumentsgrid.permission.DEADLY_ACTIVITY"), Toast.LENGTH_SHORT).show();
                }


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
            fragmentTransaction.replace(R.id.monument_web_container, monumentsWeb, TAG_RETAINED_WEB_FRAGMENT);
            fragmentTransaction.addToBackStack(TAG_RETAINED_WEB_FRAGMENT);
            fragmentTransaction.commit();

            mFragmentManager.executePendingTransactions();
        }

        // Call Method to Load URL in WebView

        if(monumentsWeb.getLoadedIndex() != position) {
            Log.i(TAG, "In Get Loaded Index  : ");
            monumentsWeb.showMonumentAtIndex(position);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Toast.makeText(this, "Toast is made", Toast.LENGTH_LONG).show();
        loadGallery();
    }

    public void loadGallery() {
        Intent intent = new Intent();
        intent.setAction("com.vasanthpandiarajan.monumentsgrid.OPEN_GRID");
        sendBroadcast(intent);
    }

}
