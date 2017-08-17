package com.valentun.findgift;

import android.app.Application;

import com.valentun.findgift.core.main.dagger.AppComponent;
import com.valentun.findgift.core.main.dagger.DaggerAppComponent;
import com.valentun.findgift.persistence.CurrenciesManager;
import com.valentun.findgift.persistence.SessionManager;

//TODO change auth activity's UI
//--------------------------------------
//TODO credentials page
//TODO settings page
//--------------------------------------

public class GiftApplication extends Application {
    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SessionManager.with(getApplicationContext());
        CurrenciesManager.with(getApplicationContext());

        appComponent = buildComponent();
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.create();
    }
}
