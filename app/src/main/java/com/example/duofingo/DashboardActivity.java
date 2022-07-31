package com.example.duofingo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    Button topicSelect;
    RecyclerView continueReadingRV;
    ArrayList<String> continueReadingDataSource;

    RecyclerView dashBoardRankingRv;
    ArrayList<DashBoardRankingDataSourceSet> dashBoardRankingDataSource;

    DashBoardRankingAdapter dashBoardRankingAdapter;
    Button chapterSelect;
    Button dashboardDesign;
    Button quizPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Recycler View populate for continue Reading
        continueReadingDataSource = new ArrayList<>();

        continueReadingDataSource.add("2/12");
        continueReadingDataSource.add("1/12");
        continueReadingDataSource.add("4/12");
        continueReadingDataSource.add("5/12");
        continueReadingDataSource.add("10/12");

        continueReadingRV = findViewById(R.id.continueReadingRecycleView);
        continueReadingRV.setHasFixedSize(true);
        continueReadingRV.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL,false));
        continueReadingRV.setAdapter(new MyContinueReadingAdapter(continueReadingDataSource, DashboardActivity.this));


        // Recycle View Data for Ranking
        dashBoardRankingDataSource = new ArrayList<>();
        dashBoardRankingDataSource.add(new DashBoardRankingDataSourceSet("Jai","1", "12"));
        dashBoardRankingDataSource.add(new DashBoardRankingDataSourceSet("Vignesh","5", "42"));
        dashBoardRankingDataSource.add(new DashBoardRankingDataSourceSet("Mv","7", "52"));
        dashBoardRankingDataSource.add(new DashBoardRankingDataSourceSet("JaiVm","17", "172"));
        dashBoardRankingDataSource.add(new DashBoardRankingDataSourceSet("JaiRtre","21", "212"));

        dashBoardRankingRv = findViewById(R.id.dashBoardRankingRecycleView);
        dashBoardRankingRv.setHasFixedSize(true);
        dashBoardRankingRv.setLayoutManager(new LinearLayoutManager(this));
        dashBoardRankingRv.setAdapter(new DashBoardRankingAdapter(dashBoardRankingDataSource, this));


        topicSelect = findViewById(R.id.topic_selection);
        topicSelect.setOnClickListener(v -> openTopicSelectActivity());

//        chapterSelect = findViewById(R.id.chapter_selection);
//        chapterSelect.setOnClickListener(v -> openChaptersSelectActivity());
//
//        dashboardDesign = findViewById(R.id.dashboard_design);
//        dashboardDesign.setOnClickListener(v -> openDashboardDesignActivity());
//
//        quizPlay = findViewById(R.id.quiz_play);
//        quizPlay.setOnClickListener(v -> openQuizPlayActivity());
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

    private void openQuizPlayActivity() {
        Intent intent = new Intent(this, QuizStartActivity.class);
        startActivity(intent);
    }
}