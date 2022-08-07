package com.example.duofingo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button getStarted, discussions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getStarted = findViewById(R.id.get_started_button);
        discussions = findViewById(R.id.discussions);

        discussions.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Log.i("LOG", "In Main");
                   Intent intent = new Intent(MainActivity.this,
                           DiscussionBoard.class);
                   intent.putExtra("username", "SRK");
                   startActivity(intent);
               }
           }
        );

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginSignUp();
            }
        });
    }

    public void openLoginSignUp() {
        Intent intent = new Intent(MainActivity.this, LoginSignUp.class);
        startActivity(intent);
    }
}