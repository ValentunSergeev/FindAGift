package com.valentun.findgift.persistence;

import android.content.Context;

import com.valentun.findgift.utils.PreferenceUtils;

import java.util.HashMap;

import static com.valentun.findgift.Constants.ACCESS_TOKEN_KEY;
import static com.valentun.findgift.Constants.CLIENT_KEY;
import static com.valentun.findgift.Constants.UID_KEY;

public class SessionManager {
    private static Context appContext;

    public static void with(Context context) {
        SessionManager.appContext = context.getApplicationContext();
    }

    public static void startSession(String uid, String client, String accessToken) {
        HashMap<String, String> values = buildValues(uid, client, accessToken);
        PreferenceUtils.putStringValues(appContext, values);
    }

    public static void destroySession() {
        PreferenceUtils.deleteValues(appContext, UID_KEY, CLIENT_KEY, ACCESS_TOKEN_KEY);
    }

    public static boolean isSessionStarted() {
        return PreferenceUtils.isPresent(appContext, ACCESS_TOKEN_KEY);
    }

    public static HashMap<String, String> getAuthHeaders() {
        HashMap<String, String> headers = new HashMap<>();

        headers.put(ACCESS_TOKEN_KEY, getString(ACCESS_TOKEN_KEY));
        headers.put(CLIENT_KEY, getString(CLIENT_KEY));
        headers.put(UID_KEY, getString(UID_KEY));

        return headers;
    }

    private static HashMap<String, String> buildValues(String uid, String client, String accessToken) {
        HashMap<String, String> values = new HashMap<>();

        values.put(UID_KEY, uid);
        values.put(CLIENT_KEY, client);
        values.put(ACCESS_TOKEN_KEY, accessToken);

        return values;
    }

    private static String getString(String key) {
        return PreferenceUtils.getString(appContext, key);
    }

}
