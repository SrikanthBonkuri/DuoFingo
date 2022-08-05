package com.example.duofingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ChapterSelectionActivity extends AppCompatActivity {

    ArrayList<String> chapters = new ArrayList<>();

    RecyclerView chapterRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_selection);

        Bundle extras = getIntent().getExtras();
        String currentTopic = extras.getString("topic");

        TopicNameToChapterList topicNameToChapterList = new TopicNameToChapterList();

        Map<String, String[]> map = topicNameToChapterList.map;

        String[] chapterNames = map.get(currentTopic);

        chapters.addAll(Arrays.asList(chapterNames));

        chapterRecyclerView = findViewById(R.id.chapter_recycler_view);
        //topicRecyclerView.setHasFixedSize(true);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChapterSelectionActivity.this);
        chapterRecyclerView.setLayoutManager(linearLayoutManager);
        chapterRecyclerView.setAdapter(new ChapterAdapter(chapters, ChapterSelectionActivity.this));

    }
}