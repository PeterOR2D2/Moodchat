package com.example.mentalhealthlogin;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.app.AlarmManager;
import android.app.PendingIntent;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.EditText;

import java.util.Calendar;


public class AddNotification extends AppCompatActivity{

    public EditText NotificationName;
    public TimePicker NotificationTime;
    public DatePicker NotificationDate;
    public static final int NOTIFICATION_ID = 42;
    final Calendar NotifDay = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);
        createNotificationChannel();

        Button addNotif = findViewById(R.id.button10);
        NotificationName = findViewById(R.id.NotifName);
        NotificationTime = (TimePicker) findViewById(R.id.NotifTime);
        NotificationDate = (DatePicker) findViewById(R.id.NotifDate);
        int hour = NotificationTime.getCurrentHour();
        int min = NotificationTime.getCurrentMinute();
        NotifDay.set(Calendar.SECOND, 0);
        NotifDay.set(Calendar.MINUTE, min);
        NotifDay.set(Calendar.HOUR, hour);
        NotifDay.set(Calendar.MONTH, NotificationDate.getMonth());
        NotifDay.set(Calendar.DAY_OF_MONTH, NotificationDate.getDayOfMonth());
        NotifDay.set(Calendar.YEAR, NotificationDate.getYear());

        addNotif.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent( AddNotification.this, NotificationSend.class );
                PendingIntent pendingIntent = PendingIntent.getBroadcast( AddNotification.this, 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long timeToSet = NotifDay.getTimeInMillis();

                alarmManager.set(AlarmManager.RTC_WAKEUP, timeToSet, pendingIntent);
            }
        });
    }

    public class NotificationSend extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent){
            NotificationCompat.Builder newNotif = new NotificationCompat.Builder(AddNotification.this, "notifyMoodChat")
                    .setSmallIcon(R.drawable.new_notif_icon)
                    .setContentTitle(NotificationName.getText().toString())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(NOTIFICATION_ID, newNotif.build());
        }
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= 26) {
            CharSequence channelname = "MoodChatNotificationChannel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyMoodChat", channelname, importance);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void HomebuttonNotification(View view)
    {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
