package com.example.duofingo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    Button topicSelect;
    RecyclerView continueReadingRV;
    ArrayList<String> continueReadingDataSource;
    LinearLayoutManager linearLayoutManagerContinueReading;
    MyContinueReadingAdapter myContinueReadingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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


        topicSelect = findViewById(R.id.topic_selection);
        topicSelect.setOnClickListener(v -> openTopicSelectActivity());
    }

    private void openTopicSelectActivity() {
        Intent intent = new Intent(this, TopicSelectionActivity.class);
        startActivity(intent);
    }
}