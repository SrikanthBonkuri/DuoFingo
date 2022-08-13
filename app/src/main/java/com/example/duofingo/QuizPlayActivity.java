package com.example.duofingo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class QuizPlayActivity extends AppCompatActivity {

    //    timer
    CountDownTimer cTimer = null;
    long countDownInMilliSecond = 20000;
    long countDownInterval = 1000;
    long timeLeftMilliSeconds = 0;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<String> ColorStateList = new ArrayList<>();


    int score = 0;
    int correct = 0;
    int wrong = 0;
    int skip = 0;
    int qIndex = 0;
    int updateQueNo = 1;

    ArrayList<String> userQuestions, userOptions, userAnswers;

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

    QuestionType quizType;

    Button timeOverOk;
    Button wrongOK;
    Button correctOK;
    TextView tvScore;
    TextView tvWrongDialogCorrectAns;
    String userName;
    String topicName = "";
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

        Bundle extras = getIntent().getExtras();
        userName = extras.getString("userName");

        if (QuestionType.WEEKLY == extras.get("quizType"))
        {
            quizType = QuestionType.WEEKLY;
        }
        else if (QuestionType.MONTHLY == extras.get("quizType"))
        {
            quizType = QuestionType.MONTHLY;
        }
        else {
            topicName = extras.getString("topicName");
            quizType = QuestionType.CHAPTER;
        }
        questions = (ArrayList<String>) getIntent().getSerializableExtra("quizQuestions");
        answers = (ArrayList<String>) getIntent().getSerializableExtra("quizAnswers");
        options = (ArrayList<String>) getIntent().getSerializableExtra("quizOptions");

        Log.i("QuizPlay Questions", questions.toString());
        Log.i("QuizPlay Answers", answers.toString());
        Log.i("QuizPlay Option", options.toString());

        radioButton1.setText(options.get(qIndex * 4)); // 2*4=8
        radioButton2.setText(options.get(qIndex * 4 + 1)); // 2*4+1=9
        radioButton3.setText(options.get(qIndex * 4 + 2)); // 2*4+2=10
        radioButton4.setText(options.get(qIndex * 4 + 3));

        /*userQuestions = new ArrayList<>();
        userAnswers = new ArrayList<>();
        userOptions = new ArrayList<>();

        // Load questions data from the DB
        getQuestionsDataFromDB(new FirestoreCallback() {
            @Override
            public void onCallBack(ArrayList<String> userQuestions, ArrayList<String> userOptions, ArrayList<String> userAnswers) {
                Log.i("Quiz", userQuestions.toString());
                Log.i("Quiz", userAnswers.toString());
                Log.i("Quiz", userOptions.toString());
                questions = (ArrayList<String>) userQuestions.clone();
                answers = (ArrayList<String>) userAnswers.clone();
                options = (ArrayList<String>) userOptions.clone();

            }
        });*/



        // check options selected or not
        // if selected then selected option correct or wrong
        nextQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radiogrp.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(QuizPlayActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                } else {
                    showNextQuestion();
                }
            }
        });

        tv_noOfQues.setText(updateQueNo + "/10");
        tv_question.setText(questions.get(qIndex));

        timeLeftMilliSeconds = countDownInMilliSecond;
        statCountDownTimer();

    }

    private interface FirestoreCallback {
        void onCallBack(ArrayList<String> userQuestions, ArrayList<String> userOptions,
                        ArrayList<String> userAnswers);
    }

    // This method gets questions, answers and the list of answers.
    private void getQuestionsDataFromDB(FirestoreCallback callback) {

        AdaptableQuizContent adaptableQuizContent = new AdaptableQuizContent(quizType, userName,
                topicName);
        ArrayList<String> userSpecificQuestions = adaptableQuizContent.getUserAdaptableQuizzes();
        Log.i("Quiz", userSpecificQuestions.toString());

        db.collection("questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        if (userSpecificQuestions.contains(
                                documentSnapshot.getString("question"))) {
                            userQuestions.add(documentSnapshot.getString("question"));
                            Log.i("Adaptable Question", documentSnapshot.toString());
                            ArrayList<String> dbOptions = (ArrayList<String>) documentSnapshot.get("answers");
                            int answerIndex = ((Long) documentSnapshot.get("correctAnswer")
                            ).intValue() - 1;
                            userOptions.addAll(dbOptions);
                            userAnswers.add(dbOptions.get(answerIndex));
                            callback.onCallBack(userQuestions, userOptions, userAnswers);
                        }
                    }
                }
            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void showNextQuestion() {

        checkAnswer();

            if (updateQueNo < 10) {
                tv_noOfQues.setText(updateQueNo+1 + "/10");
                updateQueNo++;
            }
            if (qIndex <= questions.size() - 1) {
                tv_question.setText(questions.get(qIndex));
                System.out.println(questions.size());
                System.out.println(qIndex);
                System.out.println(options.size());
                //System.out.println(quizQuestions.size());
                //System.out.println(quizQuestions.size());
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
                System.out.println(qIndex);
                System.out.println(checkedAnswer);
                System.out.println(answers.get(qIndex));
                String ans = answers.get(qIndex);
                System.out.println(checkedAnswer == ans);
                if (checkedAnswer.equals(answers.get(qIndex))) {
                    System.out.println("corr");
                    correct++;
                    txt_play_score.setText("Score : " + correct);
                    correctAlertDialog();
                    if(cTimer!=null) {
                        cTimer.cancel();
                    }
                } else {
                    System.out.println("wrong");
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
        tvScore.setText("Score : " + correct);

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

                if (timeLeftMilliSeconds < 5000) {
                    quiz_timer.setTextColor(Color.RED);
                } else {
                    quiz_timer.setTextColor(defaultColor);
                }
            }

            public void onFinish() {
                //mTextField.setText("done!");
                //showNextQuestion();
                if(cTimer!=null) {
                    //cTimer.cancel();
                    showNextQuestion();
                }
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
            new AlertDialog.Builder(this).setTitle("Confirm QUIT!").setMessage("This will lose the current progress. Are you sure you want to quit?")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(cTimer!=null) {
                                cTimer.cancel();
                            }
                            finish();
                            //super.onBackPressed();
                        }
                    }).show();
    }

}

