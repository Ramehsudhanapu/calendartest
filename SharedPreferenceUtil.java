package com.richlabz.myfunctionhall.commonutilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Ramesh on 9/8/2015.
 */
public class SharedPreferenceUtil {
    private static final String fileName ="myfunctionhal";
    private static SharedPreferences.Editor editor;
    static SharedPreferences preferences = null;
    private SharedPreferenceUtil() {
    }
    public static SharedPreferenceUtil getInstance(Context context) {
        preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = preferences.edit();
        return new SharedPreferenceUtil();
    }
    public void saveKey(String key, String value) {
        Log.d(this.toString(), key + ", " + value);
        editor.putString(key, value).commit();
    }
    public String getKey(String key) {
        return preferences.getString(key, "");
    }
    public String getUserId() {
        String userid = preferences.getString("userid", null);
        return userid;
    }
    public String getName() {
        String name = preferences.getString("name", null);
        return name;
    }
    public String getEmail() {
        String email = preferences.getString("email", null);
        return email;
    }
    public String getMobilenumber()
    {
        String Mobilenumber =preferences.getString("Mobilenumber",null);
        return Mobilenumber;
    }
    public String getOtp()
    {
        String otp =preferences.getString("Otp",null);
        return otp;
    }
   public void removeKey(String key) {
        editor.remove(key).commit();
    }
}
