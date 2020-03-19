package com.example.mentalhealthlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

public class DisplayFoodLog extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private ListView listview;
    private FoodLogAdapter arrayAdapter;
    private List food_log = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_food_log);
        listview = findViewById(R.id.food_view);

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
                            ArrayList<String> food_info = new ArrayList<>();
                            if(task.isSuccessful())
                            {
                                DocumentSnapshot doc = task.getResult();
                                List<Map<String, Object>> list = (List<Map<String, Object>>) doc.get("foodlog");
                                for(Map<String, Object> l: list)
                                {
                                    for (Map.Entry<String, Object> entry : l.entrySet()) {
                                        if(entry.getValue().getClass().equals(HashMap.class))
                                        {
                                            Object o = entry.getValue();
                                            Map x = (Map) o;
                                            for(Object yu: x.values())
                                            {
                                                food_info.add(yu.toString());
                                                //result += yu.toString() + " ";
                                            }
                                        }
                                        else
                                        {
                                            food_info.add(entry.getValue().toString());
                                        }
                                    }
                                    food_log.add(new FoodInfo(food_info.get(0), food_info.get(1)));
                                    food_info = new ArrayList<>();
                                    //test.setText(result);
                                }
                                if(food_log.size() < 3)
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(DisplayFoodLog.this);
                                    builder.setTitle("Please, try to get at least 3 meals a day!");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                                }
                                arrayAdapter = new FoodLogAdapter(DisplayFoodLog.this, R.layout.food_log_view, food_log);
                                listview.setAdapter(arrayAdapter);

                            }
                        }
                    });
                }
            }
        });
    }

    public void HomeButtonforDisplayFood(View view)
    {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
