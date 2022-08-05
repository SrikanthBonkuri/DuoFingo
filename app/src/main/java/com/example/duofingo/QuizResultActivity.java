package com.example.duofingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class QuizResultActivity extends AppCompatActivity {

    int totalScore = 0;
    int correct = 0;
    int wrong = 0;
    int skip = 0;
    boolean isKey = false;

    TextView tvScore;
    TextView tvright;
    TextView tvwrong;
    TextView tvSkip;
    TextView tvPlayAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        Intent intent = getIntent();
        totalScore = intent.getIntExtra("correct", 0);
        wrong = intent.getIntExtra("wrong", 0);
        skip = intent.getIntExtra("skip", 0);

        tvScore = findViewById(R.id.tvScore);
        tvright = findViewById(R.id.tvright);
        tvwrong = findViewById(R.id.tvwrong);
        tvSkip = findViewById(R.id.tvSkip);
        tvPlayAgain = findViewById(R.id.tvPlayAgain);

        tvScore.setText("Score: " + totalScore);
        tvright.setText("Correct: " + totalScore);
        tvwrong.setText("Wrong: " + wrong);
        tvSkip.setText("Skip: " + skip);
        if (totalScore >= 6) {
            Toast.makeText(this, "Wow Great", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Need Improvement", Toast.LENGTH_SHORT).show();
        }

        tvPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}