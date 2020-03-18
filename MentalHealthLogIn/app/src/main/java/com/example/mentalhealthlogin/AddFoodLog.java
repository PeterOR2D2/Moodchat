package com.example.mentalhealthlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

public class AddFoodLog extends AppCompatActivity {

    private TextView food_name;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_add_food_log);
        food_name = findViewById(R.id.foodname);
    }

    public void add_food(View view)
    {
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final String timestamp_result_food = new SimpleDateFormat("MM/hh/yyyy hh:mm:ss a").format(timestamp);
        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    for(QueryDocumentSnapshot document: task.getResult())
                    {
                        if(document.get("email").toString().equals(mAuth.getCurrentUser().getEmail()))
                        {
                            Map<String, Object> create_food = new HashMap<>();
                            create_food.put("ate_at", timestamp_result_food);
                            create_food.put("food", food_name.getText().toString());
                            String name = document.get("username").toString();
                            db.collection("Users").document(name).update("foodlog", FieldValue.arrayUnion(create_food));
                            Toast.makeText(AddFoodLog.this, "Successfully added food", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public void HomebuttonFood(View view)
    {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
