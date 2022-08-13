package com.example.duofingo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button getStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getStarted = findViewById(R.id.get_started_button);
        getStarted.setOnClickListener(v -> openLoginSignUp());
    }

    public void openLoginSignUp() {
        Intent intent = new Intent(MainActivity.this, LoginSignUp.class);
        startActivity(intent);
    }
}