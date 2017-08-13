package com.valentun.findgift;

import android.app.Application;

import com.valentun.findgift.persistence.CurrenciesManager;
import com.valentun.findgift.persistence.SessionManager;

//TODO improve round algorithm
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
        CurrenciesManager.with(getApplicationContext());
    }
}
