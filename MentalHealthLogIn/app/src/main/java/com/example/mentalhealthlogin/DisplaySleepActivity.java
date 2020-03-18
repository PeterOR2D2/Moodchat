package com.example.mentalhealthlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplaySleepActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private ListView listView;
    private List sleep_log = new ArrayList<>();
    private SleepListAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sleep);
        listView = findViewById(R.id.sleep_view);
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
                            ArrayList<String> sleep_info = new ArrayList<>();
                            if(task.isSuccessful())
                            {
                                DocumentSnapshot doc = task.getResult();
                                List<Map<String, Object>> list = (List<Map<String, Object>>) doc.get("sleeplog");
                                for(Map<String, Object> l: list)
                                {
                                    for (Map.Entry<String, Object> entry : l.entrySet()) {
                                        if(entry.getValue().getClass().equals(HashMap.class))
                                        {
                                            Object o = entry.getValue();
                                            Map x = (Map) o;
                                            for(Object yu: x.values())
                                            {
                                                sleep_info.add(yu.toString());
                                                //result += yu.toString() + " ";
                                            }
                                        }
                                        else
                                        {
                                            sleep_info.add(entry.getValue().toString());
                                        }
                                    }
                                    if(sleep_info.get(2).matches("([0-9]|0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])\\s*([AaPp][Mm])"))
                                    {
                                        sleep_log.add(new SleepInfo(sleep_info.get(3), sleep_info.get(1), sleep_info.get(2), sleep_info.get(0)));;
                                    }
                                    else
                                    {
                                        sleep_log.add(new SleepInfo(sleep_info.get(2), sleep_info.get(1), sleep_info.get(3), sleep_info.get(0)));;
                                    }
                                    sleep_info = new ArrayList<>();
                                    //test.setText(result);
                                }
                                arrayAdapter = new SleepListAdapter(DisplaySleepActivity.this, R.layout.sleep_log_view, sleep_log);
                                listView.setAdapter(arrayAdapter);
                            }
                        }
                    });
                }
            }
        });
    }

    public void HomeButtonSleepDisplay(View view)
    {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
