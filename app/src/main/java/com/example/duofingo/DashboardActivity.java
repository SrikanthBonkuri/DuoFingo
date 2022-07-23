package com.example.duofingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    Button topicSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        topicSelect = findViewById(R.id.topic_selection);
        topicSelect.setOnClickListener(v -> openTopicSelectActivity());
    }

    private void openTopicSelectActivity() {
        Intent intent = new Intent(this, TopicSelectionActivity.class);
        startActivity(intent);
    }
}