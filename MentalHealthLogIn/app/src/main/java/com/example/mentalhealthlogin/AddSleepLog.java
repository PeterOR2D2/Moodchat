package com.example.mentalhealthlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class AddSleepLog extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText sleepEnviron;
    private EditText sleepQuality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sleep_log);
        mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
        sleepEnviron  = findViewById(R.id.sleepEnvironment);
        sleepQuality = findViewById(R.id.sleepQuality);
    }

    public void addSleep(View view)
    {
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp.toString());
        System.out.println(sleepEnviron.getText().toString());
        System.out.println(sleepQuality.getText().toString());
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
                            sleep_log_status.put("enviroment", sleepEnviron.getText().toString());
                            sleep_log_status.put("quality", sleepQuality.getText().toString());
                            create_sleep.put("atslept", timestamp.toString());
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
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
