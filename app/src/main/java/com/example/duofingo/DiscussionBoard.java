package com.example.duofingo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DiscussionBoard extends AppCompatActivity {

    DatabaseReference mChatData, mUserData;
    ListView conversationList;
    FirebaseListAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_board);


        Log.i("LOG", "In Dicussion Board");
        conversationList = findViewById(R.id.messages_view);

        // Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance(
                "https://duofingo-58001-default-rtdb.firebaseio.com/");

        mChatData = database.getReference("discussion_board");

        //Add reference to ListView
        FirebaseListOptions<ChatMessage> options =
                new FirebaseListOptions.Builder<ChatMessage>()
                        .setQuery(mChatData, ChatMessage.class)
                        .setLayout(R.layout.their_message)
                        .build();
        Log.i("LOG", options.toString());

        adapter = new FirebaseListAdapter<ChatMessage>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull ChatMessage model, int position) {
                Log.i("DB Chats", model.toString());
                TextView name = findViewById(R.id.name);
                TextView message = findViewById(R.id.message_body);
                name.setText(model.getMessageUser());
                message.setText(model.getMessageText());

            }
        };
        Log.i("LOG", adapter.toString());

        conversationList.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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