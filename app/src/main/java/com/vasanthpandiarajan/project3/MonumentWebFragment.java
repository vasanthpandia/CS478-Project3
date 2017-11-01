package com.vasanthpandiarajan.project3;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;


public class MonumentWebFragment extends Fragment {
    private static final String TAG = "MonumentWebFragment";
    private int mCurrIdx = -1;
    private int monumentUrlsLen;

    WebView monumentView;

    int getLoadedIndex() {
        return mCurrIdx;
    }

    // Called on ListSelection - loads the url inside webview on select
    void showMonumentAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= monumentUrlsLen)
            return;
        mCurrIdx = newIndex;
        monumentView.loadUrl(MainActivity.monument_urls_list[newIndex]);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Entered Web OnCreate()");
        super.onCreate(savedInstanceState);

        // To retain instance of fragment when configuration is changed
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monument_web, container, false);
    }

    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        // Get webview from the layout and set as webview client

        monumentView = (WebView) getActivity().findViewById(R.id.monumentView);
        monumentView.setWebViewClient(new WebViewClient());
        monumentView.getSettings().setJavaScriptEnabled(true);

        monumentUrlsLen = MainActivity.monument_urls_list.length;

        if(mCurrIdx != -1) {
            // Load URL at position if current position mCurrIdx value is present.
            monumentView.loadUrl(MainActivity.monument_urls_list[mCurrIdx]);
        }

    }

    @Override
    public void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }


    @Override
    public void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }

    @Override
    public void onDetach() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDetach()");
        super.onDetach();
    }


    @Override
    public void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroyView()");
        super.onDestroyView();
    }

}
