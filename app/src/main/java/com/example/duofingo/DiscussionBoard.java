package com.example.duofingo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DiscussionBoard extends AppCompatActivity {

    DatabaseReference mChatData, mUserData;
    RecyclerView conversationList;
    MessageAdapter adapter;
    List<ChatMessage> chatList;
    String userName;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_board);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString("username");
            Log.i("DB username", userName);
        }

        chatList = new ArrayList<>();
        adapter = new MessageAdapter(DiscussionBoard.this, chatList, userName);

        Log.i("LOG", "In Discussion Board");
        conversationList = findViewById(R.id.recyclerViewChatList);
        conversationList.setAdapter(adapter);
        conversationList.setLayoutManager(new LinearLayoutManager(this));

        // Firebase
        database = FirebaseDatabase.getInstance().getReferenceFromUrl(
                "https://duofingo-58001-default-rtdb.firebaseio.com/");
        mChatData = database.child("discussion_board");

        Log.i("DB", mChatData.toString());
        getConversationHistory();
    }

    private void getConversationHistory() {
        mChatData.addValueEventListener(
            new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    updateConversation(snapshot);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }
//        conversationList.setHasFixedSize(true);
//        conversationList.setLayoutManager(new LinearLayoutManager(this));
//        conversationList.setAdapter(new MessageAdapter(DiscussionBoard.this, chatList, userName));

    private void updateConversation(DataSnapshot snapshot) {
        // Clear the current list
        chatList.clear();

        Log.i("RDB", "here");
        String message, user;
        long timestamp;

        for (DataSnapshot currentChat : snapshot.getChildren()) {
            Log.i("DB", currentChat.getValue().toString());
            message = currentChat.child("messageText").getValue().toString();
            user = currentChat.child("messageUser").getValue().toString();
            Log.i("DB User", user);
            timestamp = (long) currentChat.child("messageTime").getValue();
            ChatMessage chatMessage;
            if (userName.equals(user)){
                chatMessage = new ChatMessage(message, user, timestamp, false);
            }
            else {
                chatMessage = new ChatMessage(message, user, timestamp);
            }
            chatList.add(chatMessage);
        }
    }

    public void sendMessage(View view) {
        EditText input = (EditText)findViewById(R.id.inputText);
        Log.i("DB", "Sending message");

        // Read the input field and push a new instance
        // of ChatMessage to the Firebase database
        mChatData.push().setValue(
                new ChatMessage(input.getText().toString(),"SRK")
        );

        // Clear the input
        input.setText("");
    }
}