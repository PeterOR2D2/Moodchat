package com.example.mentalhealthlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.text.method.ScrollingMovementMethod;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.model.Values;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Object;


public class DisplayActivityLog extends AppCompatActivity {

    private TextView test;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private List activity_list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_log);
        listView = findViewById(R.id.acitvity_view);

        mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
        db.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                boolean found = false;
                String name = "";
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                {
                    if(documentSnapshot.get("email").toString().equals(mAuth.getCurrentUser().getEmail()))
                    {
                        found = true;
                        name = documentSnapshot.get("username").toString();
                    }
                }
                if(found)
                {
                    db.collection("Users").document(name).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            String result = "";
                            if(task.isSuccessful())
                            {
                                DocumentSnapshot doc = task.getResult();
                                List<Map<String, Object>> list = (List<Map<String, Object>>) doc.get("activitylog");
                                for(Map<String, Object> l: list)
                                {
                                    for (Map.Entry<String, Object> entry : l.entrySet()) {
                                        if(entry.getValue().getClass().equals(HashMap.class))
                                        {
                                          Object o = entry.getValue();
                                          Map x = (Map) o;
                                          for(Object yu: x.values())
                                          {
                                              result += yu.toString() + " ";
                                          }
                                        }
                                        else
                                        {
                                            result += entry.getValue().toString() + "\n\n";
                                        }
                                    }
                                    System.out.println(result);
                                    activity_list.add(result);
                                    result = "";
                                    //test.setText(result);
                                }
                                arrayAdapter = new ArrayAdapter(DisplayActivityLog.this, android.R.layout.simple_list_item_1, activity_list);
                                listView.setAdapter(arrayAdapter);

                            }
                        }
                    });
                }
            }
        });
        display_activity();
    }

    public void display_activity()
    {
        System.out.println(activity_list);
    }

    public void HomebuttonDisplay(View view)
    {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
