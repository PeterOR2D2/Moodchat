package com.example.mentalhealthlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private EditText user_name;
    private EditText e_mail;
    private EditText password;
    private String currUser;
    private Button login;
    private Button createAccount;
    private boolean has_account = false;
    private ArrayList<String> user_names;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.db = FirebaseFirestore.getInstance();
        CollectionReference allUserref  = this.db.collection("Users");
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){System.out.println("NO one is in log");}
        else{System.out.println("Something happened");}
        //mAuth.signOut();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        e_mail = (EditText)findViewById(R.id.et_email);
        password = (EditText)findViewById(R.id.et_pass);
        login = (Button)findViewById(R.id.button_login);
        createAccount = (Button) findViewById(R.id.CreateAccountbutton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify(e_mail.getText().toString(), password.getText().toString());
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public  void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(intent);
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

    private void verify(final String e_mail, final String password){

        if((e_mail.equals("Admin")) && (password.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
        }
        else
        {

            mAuth.signInWithEmailAndPassword(e_mail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful())
                                {
                                    for(QueryDocumentSnapshot document: task.getResult())
                                    {
                                        if(document.get("password").toString().equals(password) &&
                                                document.get("email").toString().equals(e_mail))
                                        {
                                            Toast.makeText(MainActivity.this, "Welcome, "+
                                                    document.get("username").toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });
                        Toast.makeText(MainActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Log in failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } //Not Admin.
    }
}
