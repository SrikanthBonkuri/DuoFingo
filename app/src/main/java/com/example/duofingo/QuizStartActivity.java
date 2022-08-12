package com.example.duofingo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class QuizStartActivity extends AppCompatActivity {

    Button playQuiz_home_btn;
    String userName;
    QuestionType quizType;
    String topicName;
    ArrayList<String> quizQuestions = new ArrayList<>();
    ArrayList<String> quizOptions = new ArrayList<>();
    ArrayList<String> quizAnswers = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DB_DASHBOARD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiuz_start);

        playQuiz_home_btn = findViewById(R.id.playQuiz_home_btn);
        playQuiz_home_btn.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        userName = extras.getString("userName");
        topicName = extras.getString("topicName");
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

        db.collection("questions").whereEqualTo("topicName", topicName).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int i = 10;
                    System.out.println("In");
                    System.out.println(task.isSuccessful());
                    System.out.println(task.getResult());
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        //if (userSpecificQuestions.contains(
                          //      documentSnapshot.getString("question"))) {
                        //quizQuestions.add(documentSnapshot.getString("question"));
                        String q = documentSnapshot.getString("question");
                        quizQuestions.add(q);
                            //ArrayList<String> options = (ArrayList<String>) documentSnapshot.get("options");

                        List<String> data = (List<String>)documentSnapshot.get("answers");
                            Long answerIndex = (Long) documentSnapshot.get("correctAnswer");
                        //quizOptions.addAll(options);
                        //quizOptions.addAll(data);
                        for(String a : data) {
                            quizOptions.add(a);
                            System.out.println(a);
                        }
                        int aI=0;
                        if(answerIndex==1) aI=0;
                        else if (answerIndex==2) aI=1;
                        else if (answerIndex==3) aI=2;
                        else if (answerIndex==4) aI=3;
                        quizAnswers.add(data.get(aI));
                        System.out.println(documentSnapshot.getString("question"));
                        //}

                        i = i - 1;
                        if(i<1) {
                            break;
                        }
                    }

                    System.out.println("Out");
                    System.out.println(quizQuestions.size());
                    System.out.println(quizOptions.size());
                    System.out.println(quizAnswers.size());
                    for(String a : quizAnswers) {
                        System.out.println(a);
                    }


                    playQuiz_home_btn.setVisibility(View.VISIBLE);
                } else {
                    System.out.println("else");
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        playQuiz_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                userName = extras.getString("userName");
                quizType = (QuestionType) extras.get("quizType");
                topicName = extras.getString("topic");

                Intent intent = new Intent(QuizStartActivity.this, QuizPlayActivity.class);
                intent.putExtra("userName", userName);
                intent.putExtra("quizType", quizType);
                intent.putExtra("topicName", topicName);
                intent.putExtra("quizQuestions", quizQuestions);
                intent.putExtra("quizAnswers", quizAnswers);
                intent.putExtra("quizOptions", quizOptions);
                startActivity(intent);
            }
        });

    }
}