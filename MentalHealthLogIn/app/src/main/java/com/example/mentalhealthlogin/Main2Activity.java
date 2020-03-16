package com.example.mentalhealthlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Main2Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void addactivitylog(View view)
    {
        Intent indent = new Intent(this, AddActivityLog.class);
        startActivity(indent);
    }

    public void addsleeplog(View view)
    {
        startActivity(new Intent(this, AddSleepLog.class));
    }

    public void addfoodlog(View view)
    {
        startActivity(new Intent(this, AddFoodLog.class));
    }

    public void displayactivitylog(View view)
    {
        startActivity(new Intent(this, DisplayActivityLog.class));
    }

    public void signOut(View view)
    {
        final String curr_email = mAuth.getCurrentUser().getEmail();
        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    for(QueryDocumentSnapshot document: task.getResult())
                    {
                        if(document.get("email").toString().equals(curr_email))
                        {
                            Toast.makeText(Main2Activity.this, document.get("username").toString()+
                                    " has been sign out", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        mAuth.signOut();
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        startActivity(intent);
    }
}
