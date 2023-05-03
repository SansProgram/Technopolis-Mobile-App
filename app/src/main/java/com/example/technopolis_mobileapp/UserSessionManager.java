package com.example.technopolis_mobileapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class UserSessionManager {

    private Context context;

    public UserSessionManager(Context context) {
        this.context = context;
    }

    public String getCurrentUser() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("current_user", null);
    }

    public void setCurrentUser(String username) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("current_user", username);
        editor.apply();
    }

    public void clearSession() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        Log.d("SessionStatus", "Session has ended");
    }
}
