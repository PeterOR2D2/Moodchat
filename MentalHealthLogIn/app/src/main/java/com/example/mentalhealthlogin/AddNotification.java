package com.example.mentalhealthlogin;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddNotification extends AppCompatActivity{

    public EditText NotificationName;
    public TimePicker NotificationTime;
    public DatePicker NotificationDate;
    public static final int NOTIFICATION_ID = 42;
    private Button addNotif;
    final Calendar NotifDay = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);
        createNotificationChannel();

        addNotif = findViewById(R.id.button10);
        NotificationName = findViewById(R.id.NotifName);
        NotificationTime = (TimePicker) findViewById(R.id.NotifTime);
        NotificationDate = (DatePicker) findViewById(R.id.NotifDate);
        final int hour = NotificationTime.getCurrentHour();
        final int min = NotificationTime.getCurrentMinute();
        NotifDay.set(Calendar.SECOND, 0);
        NotifDay.set(Calendar.MINUTE, min);
        NotifDay.set(Calendar.HOUR, hour);
        NotifDay.set(Calendar.MONTH, NotificationDate.getMonth());
        NotifDay.set(Calendar.DAY_OF_MONTH, NotificationDate.getDayOfMonth());
        NotifDay.set(Calendar.YEAR, NotificationDate.getYear());
    }

    public void addNotification(View view)
    {
        final int hour = NotificationTime.getCurrentHour();
        final int min = NotificationTime.getCurrentMinute();

        addNotif.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                long timeToSet = NotifDay.getTimeInMillis();
                Date date = NotifDay.getTime();
                DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
                NotificationCompat.Builder newNotif = new NotificationCompat.Builder(AddNotification.this, "notifyMoodChat")
                        .setSmallIcon(R.drawable.new_notif_icon)
                        .setContentTitle(NotificationName.getText().toString())
                        .setContentText(dateFormat.format(date) + " "+ hour + ":" + min)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(AddNotification.this);
                notificationManagerCompat.notify(NOTIFICATION_ID, newNotif.build());
            }
        });
    }

//    public class NotificationSend extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent){
//            NotificationCompat.Builder newNotif = new NotificationCompat.Builder(AddNotification.this, "notifyMoodChat")
//                    .setSmallIcon(R.drawable.new_notif_icon)
//                    .setContentTitle(NotificationName.getText().toString())
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                    .setAutoCancel(true);
//            System.out.println("It works");
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//            notificationManager.notify(NOTIFICATION_ID, newNotif.build());
//        }
//    }

    private void createNotificationChannel(){
        System.out.println("other test");
        if (Build.VERSION.SDK_INT >= 26) {
            System.out.println("The Test");
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
