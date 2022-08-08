package com.example.duofingo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    RecyclerView chapterContentRecyclerView;
    TextView chapterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_chapter);

        Bundle extras = getIntent().getExtras();
        String currentTopic = extras.getString("chapter");

        chapterContentRecyclerView = findViewById(R.id.chapter_content_recycler_view);
        chapterName = findViewById(R.id.CHAPTER_NAME);

        chapterName.setText(currentTopic);

        //chapter_paragraphs.add("two");
        //chapter_paragraphs.add("My very efficient mother just served us nuts");

        db.collection("chapters").whereEqualTo("heading", currentTopic).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
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
                            chapterContentRecyclerView.setAdapter(new ChapterAdapter(chapter_paragraphs, SpecificChapterActivity.this));

                            //continueReadingRV = findViewById(R.id.continueReadingRecycleView);
                            //continueReadingRV.setHasFixedSize(true);
                            //continueReadingRV.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            //continueReadingRV.setAdapter(new MyContinueReadingAdapter(continueReadingDataSource, DashboardActivity.this, DashboardActivity.this));

                        } else {
                            System.out.println("else");
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SpecificChapterActivity.this);
        //chapterContentRecyclerView.setLayoutManager(linearLayoutManager);
        //chapterContentRecyclerView.setAdapter(new ChapterAdapter(chapter_paragraphs, SpecificChapterActivity.this));
    }
}