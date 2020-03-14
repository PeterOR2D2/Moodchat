package com.example.mentalhealthlogin.mood.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.mentalhealthlogin.mood.data.SharedPreferencesHelper;

public class UpdateDayReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        int currentDay = mPreferences.getInt(SharedPreferencesHelper.KEY_CURRENT_DAY, 1);
        currentDay++;
        mPreferences.edit().putInt(SharedPreferencesHelper.KEY_CURRENT_DAY, currentDay).apply();
    }
}
