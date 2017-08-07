package com.valentun.findgift;

import android.app.Application;

import com.valentun.findgift.persistence.SessionManager;
public class GiftApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SessionManager.with(getApplicationContext());
    }
}
