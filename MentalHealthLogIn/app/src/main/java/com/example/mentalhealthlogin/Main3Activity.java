package com.example.mentalhealthlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;


public class Main3Activity extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private EditText user_name;
    private EditText e_mail;
    private EditText password;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        user_name = (EditText)findViewById(R.id.et_username_created);
        e_mail = (EditText)findViewById(R.id.et_email_created);
        password = (EditText)findViewById(R.id.et_password_created);
        submit = (Button)findViewById(R.id.submit_button);
        this.db = FirebaseFirestore.getInstance();
        CollectionReference allUserref  = this.db.collection("Users");
        mAuth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_Account(user_name.getText().toString(), e_mail.getText().toString(), password.getText().toString());
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if(mAuth.getCurrentUser()  != null)
        {

        }
    }

    private void create_Account(final String uname, final String email, final String pword)
    {
        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                boolean found = false;
                if (task.isSuccessful())
                {
                    for(QueryDocumentSnapshot document : task.getResult())
                    {
                        if(document.get("username").toString().equals(uname))
                        {
                            Toast.makeText(Main3Activity.this, "Username "+
                                    document.get("username").toString()+" exists.", Toast.LENGTH_SHORT).show();
                            found = true;
                        }
                        if(document.get("password").toString().equals(pword))
                        {
                            Toast.makeText(Main3Activity.this, "Password already exists", Toast.LENGTH_SHORT).show();
                            found = true;
                        }
                        if(document.get("email").toString().equals(uname))
                        {
                            Toast.makeText(Main3Activity.this, "Email "+
                                    document.get("email").toString()+" exists.", Toast.LENGTH_SHORT).show();
                            found = true;
                        }
                    }
                    if(!found)
                    {
                        Map<String, Object> create_user = new HashMap<>();
                        List<Map<String,Map<String, String>>> activitylog= new ArrayList<Map<String,Map<String, String>>>();
                        List<Map<String,Map<String, String>>> sleeplog= new ArrayList<Map<String,Map<String, String>>>();
                        List<Map<String,String>> foodlog= new ArrayList<Map<String, String>>();
                        List<Map<String,String>> moodlog= new ArrayList<Map<String, String>>();
                        create_user.put("username", uname);
                        create_user.put("email", email);
                        create_user.put("password", pword);
                        create_user.put("activitylog", activitylog);
                        create_user.put("sleeplog", sleeplog);
                        create_user.put("foodlog", foodlog);
                        create_user.put("moodlog", moodlog);
                        db.collection("Users").document(uname).set(create_user);
                    }
                }
                else {;}
            }
        });
        mAuth.createUserWithEmailAndPassword(email, pword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    System.out.println("X if is");
                    Toast.makeText(Main3Activity.this, uname+" has been successfully created", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Main3Activity.this, "opps", Toast.LENGTH_SHORT).show();
                    System.out.println("There is a prolem");
                }
            }
        });
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
        startActivity(intent);
    }
}
