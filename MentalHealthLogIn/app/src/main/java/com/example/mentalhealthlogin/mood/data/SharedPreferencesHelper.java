package com.example.mentalhealthlogin.mood.data;

import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private static final String TAG = "SharedPreferencesHelper";

    public static final String KEY_CURRENT_DAY = "KEY_CURRENT_DAY";

    public static final String KEY_CURRENT_MOOD = "KEY_CURRENT_MOOD";

    //for mood
    public static final String KEY_MOOD0 = "KEY_MOOD0";
    public static final String KEY_MOOD1 = "KEY_MOOD1";
    public static final String KEY_MOOD2 = "KEY_MOOD2";
    public static final String KEY_MOOD3 = "KEY_MOOD3";
    public static final String KEY_MOOD4 = "KEY_MOOD4";
    public static final String KEY_MOOD5 = "KEY_MOOD5";
    public static final String KEY_MOOD6 = "KEY_MOOD6";

    public static void saveMood(int moodIndex, int currentDay, SharedPreferences preferences) {
        preferences.edit().putInt(KEY_CURRENT_MOOD, moodIndex).apply();
        switch (currentDay) {
            case 1:
                preferences.edit().putInt(KEY_MOOD0, moodIndex).apply();
                break;
            case 2:
                preferences.edit().putInt(KEY_MOOD1, moodIndex).apply();
                break;
            case 3:
                preferences.edit().putInt(KEY_MOOD2, moodIndex).apply();
                break;
            case 4:
                preferences.edit().putInt(KEY_MOOD3, moodIndex).apply();
                break;
            case 5:
                preferences.edit().putInt(KEY_MOOD4, moodIndex).apply();
                break;
            case 6:
                preferences.edit().putInt(KEY_MOOD5, moodIndex).apply();
                break;
            case 7:
                preferences.edit().putInt(KEY_MOOD6, moodIndex).apply();
                break;
            default:
                preferences.edit().putInt(KEY_MOOD0, preferences.getInt(KEY_MOOD1, 3)).apply();
                preferences.edit().putInt(KEY_MOOD1, preferences.getInt(KEY_MOOD2, 3)).apply();
                preferences.edit().putInt(KEY_MOOD2, preferences.getInt(KEY_MOOD3, 3)).apply();
                preferences.edit().putInt(KEY_MOOD3, preferences.getInt(KEY_MOOD4, 3)).apply();
                preferences.edit().putInt(KEY_MOOD4, preferences.getInt(KEY_MOOD5, 3)).apply();
                preferences.edit().putInt(KEY_MOOD5, preferences.getInt(KEY_MOOD6, 3)).apply();
                preferences.edit().putInt(KEY_MOOD6, moodIndex).apply();
        }
    }
}
