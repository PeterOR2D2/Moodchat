package com.example.mentalhealthlogin.mood.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.mentalhealthlogin.R;
import com.example.mentalhealthlogin.mood.adapter.MoodsAdapter;
import com.example.mentalhealthlogin.mood.data.SharedPreferencesHelper;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MoodHistoryActivity extends AppCompatActivity {

    private static final String TAG = "MoodHistoryActivity";

    private RecyclerView moodsRecyclerView;

    private MoodsAdapter moodsAdapter;
    private SharedPreferences mPreferences;
    private ArrayList<Integer> moods = new ArrayList<>();
    //private ArrayList<String> comments = new ArrayList<>();
    private int currentDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);
        Log.d(TAG, "onCreate: MoodHistoryActivity");

     mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        currentDay = mPreferences.getInt(SharedPreferencesHelper.KEY_CURRENT_DAY, 1);

        moodsRecyclerView = findViewById(R.id.reycler_moods);
        moodsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        for (int i = 0; i < currentDay; i++) {
            moods.add(mPreferences.getInt("KEY_MOOD" + i, 3));
            //comments.add(mPreferences.getString("KEY_COMMENT" + i, ""));
        }

        moodsAdapter = new MoodsAdapter(this, currentDay, moods);//, comments);
        moodsRecyclerView.setAdapter(moodsAdapter);
    }
}
