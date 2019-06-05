package com.flickr;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.concurrent.atomic.AtomicInteger;


public class FlickrApplication extends Application {
    private static AtomicInteger dataRetrievalCount = new AtomicInteger(0);
    private static FlickrApplication instance;



    public FlickrApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static FlickrApplication getInstance(){
            return instance;
    }
}
