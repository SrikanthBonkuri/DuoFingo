package com.example.duofingo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpecificChapterActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DB_DASHBOARD";

    ArrayList<String> chapter_paragraphs = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    RecyclerView chapterContentRecyclerView;
    TextView chapterName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_chapter);

        Bundle extras = getIntent().getExtras();
        String currentChapter = extras.getString("chapter");
        String userName = extras.getString("userName");
        String currentTopic = extras.getString("topic");

        chapterContentRecyclerView = findViewById(R.id.chapter_content_recycler_view);
        chapterName = findViewById(R.id.CHAPTER_NAME);

        floatingActionButton = findViewById(R.id.finishChapter);
        chapterName.setText(currentChapter);

        //chapter_paragraphs.add("two");
        //chapter_paragraphs.add("My very efficient mother just served us nuts");

        db.collection("chapters").whereEqualTo("heading", currentChapter).get()
                .addOnCompleteListener(task -> {
                    System.out.println("in");
                    System.out.println(task.isSuccessful());
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
//                                System.out.println("if");
//                                chapter_paragraphs.add(document.getString("heading"));

                            List<String> data = (List<String>)document.get("body");

                            for(String a : data) {
                                chapter_paragraphs.add(a);
                            }
//                                chapter_paragraphs.add(document.get("body").);

                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SpecificChapterActivity.this);
                        chapterContentRecyclerView.setLayoutManager(linearLayoutManager);
                        chapterContentRecyclerView.setAdapter(new ChapterAdapter(chapter_paragraphs, SpecificChapterActivity.this, userName, currentTopic));

                        //continueReadingRV = findViewById(R.id.continueReadingRecycleView);
                        //continueReadingRV.setHasFixedSize(true);
                        //continueReadingRV.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        //continueReadingRV.setAdapter(new MyContinueReadingAdapter(continueReadingDataSource, DashboardActivity.this, DashboardActivity.this));

                    } else {
                        System.out.println("else");
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] Id = new String[1];
                db.collection("user_topics").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Integer currentCount = null;
                        Integer totalChapterCount = null;
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                if (Objects.equals(documentSnapshot.get("userID"), userName)
                                        && Objects.equals(documentSnapshot.get("topicName"), currentTopic)) {
                                    //Toast.makeText(getApplicationContext(), "current COUNT: " + documentSnapshot.get("chapterID").toString(), Toast.LENGTH_SHORT).show();
                                    currentCount = Integer.parseInt(documentSnapshot.get("chapterID").toString());
                                    totalChapterCount = Integer.parseInt(documentSnapshot.get("total_chapters").toString());
                                    Id[0] = documentSnapshot.getId();
                                    break;
                                }
                            }

                            if (currentCount < totalChapterCount) {
                                DocumentReference dr = db.collection("user_topics").document(Id[0]);
                                dr.update("chapterID", currentCount + 1);
                            }


                        }
                    }
                });
            }
        });
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SpecificChapterActivity.this);
        //chapterContentRecyclerView.setLayoutManager(linearLayoutManager);
        //chapterContentRecyclerView.setAdapter(new ChapterAdapter(chapter_paragraphs, SpecificChapterActivity.this));
    }
}