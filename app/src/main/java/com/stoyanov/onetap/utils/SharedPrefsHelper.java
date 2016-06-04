package com.stoyanov.onetap.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by alexander on 6/4/16.
 */
public class SharedPrefsHelper {
    private static final String PREFS_NAME = "OneTapPrefs";

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private static SharedPrefsHelper instance;

    public synchronized static SharedPrefsHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefsHelper(context);
        }
        return instance;
    }

    private SharedPrefsHelper(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }
}
