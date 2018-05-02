package com.gateway.lead_hunter.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    public static final String PREF_NAME = "com.gateway.lead_hunter.";
    public static final String FIRST_START_KEY = "first_start_key";

    private static PreferencesManager instance;
    private final SharedPreferences preferences;

    public static synchronized void initializeInstance(Context context) {
        if (instance == null) {
            instance = new PreferencesManager(context);
        }
    }

    public static synchronized PreferencesManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return instance;
    }

    private PreferencesManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public Boolean isFirstStart(){
        return preferences.getBoolean(FIRST_START_KEY, true);
    }

    public void setFirstStart(boolean firstStart) {
        preferences.edit().putBoolean(FIRST_START_KEY, firstStart).commit();
    }

}
