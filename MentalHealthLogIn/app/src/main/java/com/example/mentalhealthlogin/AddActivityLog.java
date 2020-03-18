package com.example.mentalhealthlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Timestamp;
import java.util.Date;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AddActivityLog extends AppCompatActivity {

    private TextView editeventTime;
    private TimePickerDialog timePickerDialog;
    private EditText activityame;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log);
        mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
        activityame  = findViewById(R.id.activityName);
        editeventTime = (TextView) findViewById(R.id.editEventTime);
        editeventTime.setTextColor(Color.parseColor("#000000"));
        editeventTime.setTextSize(30);
        editeventTime.setGravity(Gravity.CENTER);
        editeventTime.setOnClickListener(new View.OnClickListener() {
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddActivityLog.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        editeventTime.setText(String.format("%d hours %d minutes", hourOfDay, minute));
                    }
                }, ehour, eminute, true);
                timePickerDialog.setTitle("Choose hour:");
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
            }
        });
        editeventTime.getText().toString();
    }

    public void addActivity(View view)
    {
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final String timestamp_result_activity = new SimpleDateFormat("MM/hh/yyyy hh:mm:ss a").format(timestamp);
        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    for(QueryDocumentSnapshot document: task.getResult())
                    {
                        if(document.get("email").toString().equals(mAuth.getCurrentUser().getEmail()))
                        {
                            Map<String, Object> create_activity = new HashMap<>();
                            Map<String, String> activity_log_status = new HashMap();
                            activity_log_status.put("duration", editeventTime.getText().toString());
                            activity_log_status.put("what", activityame.getText().toString());
                            create_activity.put("activitytime", timestamp_result_activity);
                            create_activity.put("activitystatus", activity_log_status);
                            String name = document.get("username").toString();
                            db.collection("Users").document(name).update("activitylog", FieldValue.arrayUnion(create_activity));
                            Toast.makeText(AddActivityLog.this, "Successfully added a activity", Toast.LENGTH_SHORT).show();
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
