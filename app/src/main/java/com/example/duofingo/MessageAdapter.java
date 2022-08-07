package com.example.duofingo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    List<ChatMessage> messages = new ArrayList<ChatMessage>();
    Context context;
    String username;

    public MessageAdapter(Context context, List<ChatMessage> chatList, String userName) {
        this.messages = chatList;
        this.context = context;
        this.username = userName;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.their_message,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        // TODO: Ensure sender's message and receiver's message are different layouts.
        // holder.bindListData(messages.get(position), username);

        holder.messageBody.setText(messages.get(position).getMessageText());
        holder.name.setText(username);

        SimpleDateFormat sdf = new SimpleDateFormat("   MM/dd HH:mm",  Locale.US);
        String time = sdf.format(new Date((long) messages.get(position).getMessageTime()));
        holder.messageTime.setText(time);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}