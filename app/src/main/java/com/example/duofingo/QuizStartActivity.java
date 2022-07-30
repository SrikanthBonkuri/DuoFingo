package com.example.duofingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuizStartActivity extends AppCompatActivity {

    Button playQuiz_home_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiuz_start);

        playQuiz_home_btn = findViewById(R.id.playQuiz_home_btn);
        playQuiz_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizStartActivity.this, QuizPlayActivity.class);
                startActivity(intent);
            }
        });

    }
}