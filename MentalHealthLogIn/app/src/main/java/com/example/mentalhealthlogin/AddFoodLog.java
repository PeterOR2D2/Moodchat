package com.example.mentalhealthlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddFoodLog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_log);
    }

    public void Homebutton(View view)
    {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
