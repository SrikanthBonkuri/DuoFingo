package com.example.duofingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    Button topicSelect;
    Button chapterSelect;
    Button dashboardDesign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        topicSelect = findViewById(R.id.topic_selection);
        topicSelect.setOnClickListener(v -> openTopicSelectActivity());

        chapterSelect = findViewById(R.id.chapter_selection);
        chapterSelect.setOnClickListener(v -> openChaptersSelectActivity());

        dashboardDesign = findViewById(R.id.dashboard_design);
        dashboardDesign.setOnClickListener(v -> openDashboardDesignActivity());
    }

    private void openTopicSelectActivity() {
        Intent intent = new Intent(this, TopicSelectionActivity.class);
        startActivity(intent);
    }

    private void openChaptersSelectActivity() {
        Intent intent = new Intent(this, ChapterSelectionActivity.class);
        startActivity(intent);
    }

    private void openDashboardDesignActivity() {
        Intent intent = new Intent(this, DashDesignActivity.class);
        startActivity(intent);
    }
}