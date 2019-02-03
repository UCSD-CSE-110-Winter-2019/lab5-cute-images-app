package edu.ucsd.cse110.cuteimagesapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SharedPreferencesAdapter {
    private static final String PREFERENCES_NAME = "CUTE_IMAGES_APP_PREFERENCES";

    public enum Preference {
        IMAGE_SERVICE_TYPE
    }


    private static SharedPreferencesAdapter instance;

    private final SharedPreferences sharedPreferences;

    private SharedPreferencesAdapter(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void init(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesAdapter(context);
        }
    }

    public static synchronized SharedPreferencesAdapter getInstance() {
        return instance;
    }

    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    public String getValue(Preference preference, String defValue) {
        return sharedPreferences.getString(preference.name(), defValue);
    }

    public void setValue(Preference preference, String val) {
        sharedPreferences.edit().putString(preference.name(), val).apply();
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
