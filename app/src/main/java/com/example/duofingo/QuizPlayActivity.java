package com.example.duofingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
//import com.techpassmaster.quizappkotlin.databinding.ActivityPlayBinding;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class QuizPlayActivity extends AppCompatActivity {

    //    timer
    CountDownTimer cTimer = null;
    long countDownInMilliSecond = 3000;
    long countDownInterval = 1000;
    long timeLeftMilliSeconds = 0;

    ArrayList<String> ColorStateList = new ArrayList<>();


    int score = 0;
    int correct = 0;
    int wrong = 0;
    int skip = 0;
    int qIndex = 0;
    int updateQueNo = 1;

    ArrayList<String> questions = new ArrayList<>(Arrays.asList(
            "Q.1. If a computer has more than one processor then it is known as?",
            "Q.2. Full form of URL is?",
            "Q.3. One kilobyte (KB) is equal to",
            "Q.4. Father of ‘C’ programming language?",
            "Q.5. SMPS stands for",
            "Q.6. What is a floppy disk used for",
            "Q.7. Which operating system is developed and used by Apple Inc?",
            "Q.8. Random Access Memory (RAM) is which storage of device?",
            "Q.9. Who is the founder of the Internet?",
            "Q.10. Which one is the first search engine in internet?"));

    ArrayList<String> answers = new ArrayList<>(Arrays.asList(
            "Multiprocessor",
            "Uniform Resource Locator",
            "1,024 bytes",
            "Dennis Ritchie",
            "Switched mode power supply",
            "To store information",
            "iOS",
            "Primay",
            "Tim Berners-Lee",
            "Archie"));

    ArrayList<String> options = new ArrayList<>(Arrays.asList(
            "Uniprocess",
            "Multiprocessor",
            "Multithreaded",
            "Multiprogramming",
            "Uniform Resource Locator",
            "Uniform Resource Linkwrong",
            "Uniform Registered Link",
            "Unified Resource Link",
            "1,000 bits",
            "1,024 bytes",
            "1,024 megabytes",
            "1,024 gigabytes",
            "Dennis Ritchie",
            "Prof Jhon Kemeny",
            "Thomas Kurtz",
            "Bill Gates",
            "Switched mode power supply",
            "Start mode power supply",
            "Store mode power supply",
            "Single mode power supply",
            "To unlock the computer",
            "To store information",
            "To erase the computer screen",
            "To make the printer work",
            "Windows",
            "Android",
            "iOS",
            "UNIX",
            "Primay",
            "Secondary",
            "Teriary",
            "Off line",
            "Vint Cerf",
            "Charles Babbage",
            "Tim Berners-Lee",
            "None of these",
            "Google",
            "Archie",
            "Altavista",
            "WAIS"));

    TextView tv_question;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    Button nextQuestionBtn;
    RadioGroup radiogrp;
    TextView tv_noOfQues;
    TextView quiz_timer;
    int defaultColor = Color.BLACK;

    RadioButton checkedRadioButton;
    TextView txt_play_score;

    Button timeOverOk;
    Button wrongOK;
    Button correctOK;
    TextView tvScore;
    TextView tvWrongDialogCorrectAns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_play);

        tv_question = findViewById(R.id.tv_question);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);
        nextQuestionBtn = findViewById(R.id.nextQuestionBtn);
        radiogrp = findViewById(R.id.radiogrp);
        tv_noOfQues = findViewById(R.id.tv_noOfQues);
        quiz_timer = findViewById(R.id.quiz_timer);

        // check options selected or not
        // if selected then selected option correct or wrong
        nextQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radiogrp.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(QuizPlayActivity.this, "Please select an options", Toast.LENGTH_SHORT).show();
                } else {
                    showNextQuestion();
                }
            }
        });

        tv_noOfQues.setText("$updateQueNo/10");
        tv_question.setText(questions.get(qIndex));


        timeLeftMilliSeconds = countDownInMilliSecond;

        statCountDownTimer();
    }

    @SuppressLint("SetTextI18n")
    private void showNextQuestion() {

        checkAnswer();

            if (updateQueNo < 10) {
                tv_noOfQues.setText("${updateQueNo + 1}/10");
                updateQueNo++;
            }
            if (qIndex <= questions.size() - 1) {
                tv_question.setText(questions.get(qIndex));
                radioButton1.setText(options.get(qIndex * 4)); // 2*4=8
                radioButton2.setText(options.get(qIndex * 4 + 1)); // 2*4+1=9
                radioButton3.setText(options.get(qIndex * 4 + 2)); // 2*4+2=10
                radioButton4.setText(options.get(qIndex * 4 + 3)); //  2*4+3=11
            } else {
                score = correct;
                Intent intent = new Intent(QuizPlayActivity.this, QuizResultActivity.class);
                intent.putExtra("correct", correct);
                intent.putExtra("wrong", wrong);
                intent.putExtra("skip", skip);
                startActivity(intent);
                finish();
            }
            radiogrp.clearCheck();
    }

    @SuppressLint("SetTextI18n")
    private void checkAnswer() {

            txt_play_score = findViewById(R.id.txt_play_score);

            if (radiogrp.getCheckedRadioButtonId() == -1) {
                skip++;
                timeOverAlertDialog();
            } else {
                checkedRadioButton = findViewById(radiogrp.getCheckedRadioButtonId());
                String checkedAnswer = checkedRadioButton.getText().toString();
                if (checkedAnswer == answers.get(qIndex)) {
                    correct++;
                    txt_play_score.setText("Score : $correct");
                    correctAlertDialog();
                    if(cTimer!=null) {
                        cTimer.cancel();
                    }
                } else {
                    wrong++;
                    wrongAlertDialog();
                    if(cTimer!=null) {
                        cTimer.cancel();
                    }
                }
            }
            qIndex++;
    }

    @SuppressLint("SetTextI18n")
    private void correctAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.correct_answer, null);
        builder.setView(view);

        correctOK = view.findViewById(R.id.correct_ok);
        tvScore = view.findViewById(R.id.tvDialog_score);
        tvScore.setText("Score : $correct");

        AlertDialog alertDialog = builder.create();

        correctOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeLeftMilliSeconds = countDownInMilliSecond;
                statCountDownTimer();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void wrongAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.wrong_answer, null);
        builder.setView(view);

        wrongOK = view.findViewById(R.id.wrong_ok);;
        tvWrongDialogCorrectAns = view.findViewById(R.id.tv_wrongDialog_correctAns);

        tvWrongDialogCorrectAns.setText("Correct Answer : " + answers.get(qIndex));

        AlertDialog alertDialog = builder.create();

        wrongOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeLeftMilliSeconds = countDownInMilliSecond;
                statCountDownTimer();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void timeOverAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.time_over, null);
        builder.setView(view);

        timeOverOk = view.findViewById(R.id.timeOver_ok);
        AlertDialog alertDialog = builder.create();
        timeOverOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeLeftMilliSeconds = countDownInMilliSecond;
                statCountDownTimer();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void statCountDownTimer() {

        cTimer = new CountDownTimer(timeLeftMilliSeconds, countDownInterval) {

            public void onTick(long millisUntilFinished) {
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                // logic to set the EditText could go here
                timeLeftMilliSeconds = millisUntilFinished;
                long second = TimeUnit.MILLISECONDS.toSeconds(timeLeftMilliSeconds);

                // %02d format the integer with 2 digit
                String timer = String.format(Locale.getDefault(), "Time: %02d", second);
                quiz_timer.setText(timer);

                if (timeLeftMilliSeconds < 10000) {
                    quiz_timer.setTextColor(Color.RED);
                } else {
                    quiz_timer.setTextColor(defaultColor);
                }
            }

            public void onFinish() {
                //mTextField.setText("done!");
                showNextQuestion();
            }
        }.start();
    }

}

