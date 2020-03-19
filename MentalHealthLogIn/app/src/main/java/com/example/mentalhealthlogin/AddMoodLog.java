package com.example.mentalhealthlogin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.AlertDialog;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mentalhealthlogin.mood.ui.MoodHistoryActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.example.mentalhealthlogin.mood.data.SharedPreferencesHelper;
import com.example.mentalhealthlogin.mood.receiver.UpdateDayReceiver;
import com.example.mentalhealthlogin.mood.util.Constants;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

public class AddMoodLog extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private static final String TAG = "AddMoodLog";
    // Class name for Log tag
    private static final String LOG_TAG = AddMoodLog.class.getSimpleName();

    private ImageView moodImageView;
    private ImageButton moodHistoryButton;
    private ImageButton addCommentButton;
    private GestureDetectorCompat mDetector;
    private RelativeLayout parentRelativeLayout;

    private SharedPreferences mPreferences;
    private int currentDay;
    private int currentMoodIndex;
    private String currentComment;

    // [START declare_analytics]
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood_log);
        Log.d(TAG, "onCreate: AddMood");


        moodImageView = findViewById(R.id.my_mood);
        parentRelativeLayout = findViewById(R.id.parent_relative_layout);
        addCommentButton = findViewById(R.id.btn_add_comment);
        moodHistoryButton = findViewById(R.id.btn_mood_history);

        mDetector = new GestureDetectorCompat(this, this);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        currentDay = mPreferences.getInt(SharedPreferencesHelper.KEY_CURRENT_DAY, 1);
        currentMoodIndex = mPreferences.getInt(SharedPreferencesHelper.KEY_CURRENT_MOOD, 3);
        //currentComment = mPreferences.getString(SharedPreferencesHelper.KEY_CURRENT_COMMENT, "");
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        System.out.println(currentMoodIndex);
        changeUiForMood(currentMoodIndex);
        scheduleAlarm();


        //*****************************Add comment to the Mood********************************/

        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentDay = mPreferences.getInt(SharedPreferencesHelper.KEY_CURRENT_DAY, 1);
                currentMoodIndex = mPreferences.getInt(SharedPreferencesHelper.KEY_CURRENT_MOOD, 3);
                System.out.println(SharedPreferencesHelper.KEY_CURRENT_MOOD);
                System.out.println(currentMoodIndex);
                Log.d(LOG_TAG, "Button clicked!");
                AlertDialog.Builder builder = new AlertDialog.Builder(AddMoodLog.this);
                final EditText editText = new EditText(AddMoodLog.this);


                builder//.setMessage("Are you sure?")//\uD83E\uDD14 \uD83D\uDCDD").setView(editText)
                        .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //if (!editText.getText().toString().isEmpty()) {
                                //SharedPreferencesHelper.saveComment(editText.getText().toString(), currentDay, mPreferences);
                                //}

                                dialog.dismiss();
                                Toast.makeText(AddMoodLog.this, "Mood Saved", Toast.LENGTH_SHORT).show();

                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        Toast.makeText(AddMoodLog.this, "Comment Canceled", Toast.LENGTH_SHORT).show();
                    }
                })
                        ;
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);


            }
        });


        //* History Button to view Mood history screen*/
        moodHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Button clicked!");

                Intent intent = new Intent(AddMoodLog.this, MoodHistoryActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onDown(MotionEvent e) {
        // Not needed for this project
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // Not needed for this project

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // Not needed for this project
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // Not needed for this project
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // Not needed for this project

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // Swiping up
        if (e1.getY() - e2.getY() > 50) {
            if (currentMoodIndex < 4) {
                currentMoodIndex++;
                changeUiForMood(currentMoodIndex);
                SharedPreferencesHelper.saveMood(currentMoodIndex, currentDay, mPreferences);

            }

        }

        // Swiping down
        else if (e1.getY() - e2.getY() < 50) {
            if (currentMoodIndex > 0) {
                currentMoodIndex--;
                changeUiForMood(currentMoodIndex);
                SharedPreferencesHelper.saveMood(currentMoodIndex, currentDay, mPreferences);
            }
        }
        return true;
    }

    //************************* change mood methods***************************************/
    private void changeUiForMood(int currentMoodIndex) {
        moodImageView.setImageResource(Constants.moodImagesArray[currentMoodIndex]);
        parentRelativeLayout.setBackgroundResource(Constants.moodColorsArray[currentMoodIndex]);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, Constants.moodSoundsArray[currentMoodIndex]);
        mediaPlayer.start();
    }


    //* Scheduling alarm to save mood everyday
    private void scheduleAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, UpdateDayReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }
}
