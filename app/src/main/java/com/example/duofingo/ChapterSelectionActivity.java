package com.example.duofingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChapterSelectionActivity extends AppCompatActivity {

    ArrayList<String> chapters = new ArrayList<>();

    RecyclerView chapterRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_selection);

        chapters.add("Chapter 1 - Working Capital Management");
        chapters.add("Chapter 2 - Cash Flow");
        chapters.add("Chapter 3 - Interest Rates");
        chapters.add("Chapter 4 - Marketing Partnership");

        chapterRecyclerView = findViewById(R.id.chapter_recycler_view);
        //topicRecyclerView.setHasFixedSize(true);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChapterSelectionActivity.this);
        chapterRecyclerView.setLayoutManager(linearLayoutManager);
        chapterRecyclerView.setAdapter(new TopicAdapter(chapters, ChapterSelectionActivity.this));

    }
}