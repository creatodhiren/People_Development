package com.creatoweb.peopledevelopment.data;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class AppPref {


    // App preference setting
    private static final String SETTINGS_NAME = "app_settings";
    private static AppPref sSharedPrefs;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private boolean mBulkUpdate = false;


    // this class contain the all keys
    public static class Key {

        public static final String FIRST_TIME = "first_time";


        public static final String SESSION = "session";
        public static final String ID = "agent_ID";
        public static final String MOBILE = "agent_mobile";
        public static final String NAME = "agent_name";
        public static final String EMAIL = "agent_email";
        public static final String MOBILE1 = "agent_mobile1";
        public static final String PASSWORD = "password";
        public static final String Image = "agent_image";
        public static final String STATUS = "profile";
    }

    // constructor
    private AppPref(Context context) {
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }

    // mathod for getting instance of AppPref class for first time
    public static AppPref getInstance(Context context) {
        if (sSharedPrefs == null) {
            sSharedPrefs = new AppPref(context.getApplicationContext());
        }
        return sSharedPrefs;
    }


    // methods for setting the values
    public void put(String key, String val) {
        doEdit();
        mEditor.putString(key, val);
        doCommit();
    }

    public void put(String key, int val) {
        doEdit();
        mEditor.putInt(key, val);
        doCommit();
    }

    public void put(String key, boolean val) {
        doEdit();
        mEditor.putBoolean(key, val);
        doCommit();
    }

    public void put(String key, float val) {
        doEdit();
        mEditor.putFloat(key, val);
        doCommit();
    }

    public void put(String key, double val) {
        doEdit();
        mEditor.putString(key, String.valueOf(val));
        doCommit();
    }

    public void put(String key, long val) {
        doEdit();
        mEditor.putLong(key, val);
        doCommit();
    }

    public void put(String key, Set<String> val) {
        doEdit();
        mEditor.putStringSet(key, val);
        doCommit();
    }


    // methods for getting the values
    public String getString(String key, String defaultValue) {
        return mPref.getString(key, defaultValue);
    }

    public String getString(String key) {
        return mPref.getString(key, null);
    }

    public int getInt(String key) {
        return mPref.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return mPref.getInt(key, defaultValue);
    }

    public long getLong(String key) {
        return mPref.getLong(key, 0);
    }

    public long getLong(String key, long defaultValue) {
        return mPref.getLong(key, defaultValue);
    }

    public float getFloat(String key) {
        return mPref.getFloat(key, 0);
    }

    public float getFloat(String key, float defaultValue) {
        return mPref.getFloat(key, defaultValue);
    }

    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    public double getDouble(String key, double defaultValue) {
        try {
            return Double.valueOf(mPref.getString(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mPref.getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return mPref.getBoolean(key, false);
    }


  public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return mPref.getStringSet(key, defaultValue);
    }

  public Set<String> getStringSet(String key) {
        return mPref.getStringSet(key, null);
    }


    /**
     * Remove keys from SharedPreferences.
     *
     * @param keys The name of the key(s) to be removed.
     */
    public void remove(String... keys) {
        doEdit();
        for (String key : keys) {
            mEditor.remove(key);
        }
        doCommit();
    }

    /**
     * Remove all keys from SharedPreferences.
     */
    public void clear() {
        doEdit();
        mEditor.clear();
        doCommit();
    }

    public void edit() {
        mBulkUpdate = true;
        mEditor = mPref.edit();
    }

    public void commit() {
        mBulkUpdate = false;
        mEditor.commit();
        mEditor = null;
    }

    private void doEdit() {
        if (!mBulkUpdate && mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    private void doCommit() {
        if (!mBulkUpdate && mEditor != null) {
            mEditor.commit();
            mEditor = null;
        }
    }
}