package com.nepal.naxa.smartnaari.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/*public class SharedPreferenceUtils {
    public static SharedPreferences sharedPreferences;
    public static final String SHARED_PREFERENCE = "museum_sharedPreferences";
    public static void getPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }
    public static void setPreferences(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }
    public static String getPreferenceValue(String key) {
        return sharedPreferences.getString(key, "");
    }
    public static void clearPreferences() {
        sharedPreferences.edit().clear().apply();
    }
}*/

public class SharedPreferenceUtils {

    private static final String TAG = SharedPreferenceUtils.class.getSimpleName();
    private Context mContext;

    private SharedPreferences pref;
    private Editor editor;
    private static SharedPreferenceUtils sInstance;


    public static final String PREF_NETWORK_CACHE = "network_cache";
    public static final String KEY_USER_DATA = "user_data";
    public static final String KEY_OWL_LIST = "owl_list";
    public static final String KEY_HAS_USER_LEARNED_APP = "has_learned_app";

    private SharedPreferenceUtils(Context context, String name) {
        mContext = context;
        pref = context.getSharedPreferences(name, 0);
        editor = pref.edit();
    }

    public static synchronized SharedPreferenceUtils getInstance(Context context, String name) {
        if (sInstance == null) {
            sInstance = new SharedPreferenceUtils(context.getApplicationContext(), name);
        }
        return sInstance;
    }

    public void setValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void setValue(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void setValue(String key, double value) {
        setValue(key, Double.toString(value));
    }

    public void setValue(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public void setValue(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String getStringValue(String key, String defaultValue) {

        return pref.getString(key, defaultValue);
    }

    public int getIntValue(String key, int defaultValue) {
        return pref.getInt(key, defaultValue);
    }

    public long getLongValue(String key, long defaultValue) {
        return pref.getLong(key, defaultValue);
    }

    public boolean getBooleanValue(String keyFlag, boolean defaultValue) {
        return pref.getBoolean(keyFlag, defaultValue);
    }

    public void removeKey(String key) {

        if (editor != null) {
            editor.remove(key);
            editor.commit();
        }
    }

    public void clear() {
        editor.clear().commit();
    }
}