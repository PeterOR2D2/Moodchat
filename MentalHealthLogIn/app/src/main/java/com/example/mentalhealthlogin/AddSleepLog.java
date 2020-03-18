package com.example.mentalhealthlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddSleepLog extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText sleepEnviron;
    private EditText sleepQuality;
    private TextView sleeptime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sleep_log);
        mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
        sleepEnviron  = findViewById(R.id.sleepHours);
        sleepQuality = findViewById(R.id.sleepQuality);
        sleeptime = (TextView)findViewById(R.id.sleeptime);
        sleeptime.setTextColor(Color.parseColor("#000000"));
        sleeptime.setTextSize(30);
        sleeptime.setGravity(Gravity.CENTER);
        sleeptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar ecurrtime = Calendar.getInstance();
                int ehour = ecurrtime.get(Calendar.HOUR_OF_DAY);
                int eminute = ecurrtime.get(Calendar.MINUTE);
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (view.isShown()) {
                            ecurrtime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            ecurrtime.set(Calendar.MINUTE, minute);

                        }
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSleepLog.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String am_pm_value;
                        if(hourOfDay >= 12){am_pm_value="PM";}
                        else{am_pm_value="AM";}
                        sleeptime.setText(String.format("%02d:%02d"+" "+am_pm_value, hourOfDay, minute));
                    }
                }, ehour, eminute, false);
                timePickerDialog.setTitle("Choose time:");
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
            }
        });
        sleeptime.getText().toString();
    }

    public void addSleep(View view)
    {
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final String timestamp_result_sleep = new SimpleDateFormat("MM/hh/yyyy hh:mm:ss a").format(timestamp);
        System.out.println(timestamp.toString());
        System.out.println(sleepEnviron.getText().toString());
        System.out.println(sleepQuality.getText().toString());
        System.out.println(sleeptime.getText().toString());
        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    for(QueryDocumentSnapshot document: task.getResult())
                    {
                        if(document.get("email").toString().equals(mAuth.getCurrentUser().getEmail()))
                        {
                            Map<String, Object> create_sleep = new HashMap<>();
                            Map<String, String> sleep_log_status = new HashMap();
                            sleep_log_status.put("hours", sleepEnviron.getText().toString());
                            sleep_log_status.put("quality", sleepQuality.getText().toString());
                            sleep_log_status.put("time_slept", sleeptime.getText().toString());
                            create_sleep.put("atslept", timestamp_result_sleep);
                            create_sleep.put("sleepstatus", sleep_log_status);
                            String name = document.get("username").toString();
                            db.collection("Users").document(name).update("sleeplog", FieldValue.arrayUnion(create_sleep));
                            Toast.makeText(AddSleepLog.this, "Successfully added sleep", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public void Homebutton(View view)
    {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
