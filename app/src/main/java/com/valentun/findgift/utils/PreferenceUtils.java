package com.valentun.findgift.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.valentun.findgift.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Valentun on 06.08.2017.
 */

public class PreferenceUtils {
    public static String getString(Context context, String key) {
        return getPreferences(context).getString(key, Constants.PREFERENCES.DEFAULT_STRING_VALUE);
    }

    public static String getString(Context context, String key, String defaultValue) {
        return getPreferences(context).getString(key, defaultValue);
    }

    public static void putStringValues(Context context, HashMap<String, String> values) {
        SharedPreferences.Editor editor = getPreferences(context).edit();

        for (Map.Entry<String, String> entry: values.entrySet()){
            editor.putString(entry.getKey(), entry.getValue());
        }

        editor.apply();
    }

    public static void deleteValues(Context context, String... keys) {
        SharedPreferences.Editor editor = getPreferences(context).edit();

        for (String key : keys) {
            editor.remove(key);
        }

        editor.apply();
    }

    public static boolean isPresent(Context context, String key) {
        return getPreferences(context).contains(key);
    }


    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(Constants.PREFERENCES.APP_KEY, Context.MODE_PRIVATE);
    }
}
