package com.valentun.findgift;

import android.app.Application;

import com.valentun.findgift.persistence.SessionManager;

//TODO move UI changes to onResponse
//TODO change auth activity's UI
//--------------------------------------
//TODO credentials page
//TODO settings page
//--------------------------------------

public class GiftApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SessionManager.with(getApplicationContext());
    }
}
