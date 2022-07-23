package com.example.duofingo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TopicAdapter extends RecyclerView.Adapter<TopicViewHolder>{

    // the list of pairs
    private final ArrayList<String> lst;

    // the context
    private final Context context;

    public TopicAdapter(ArrayList<String> lst, Context context) {
        this.lst = lst;
        this.context = context;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopicViewHolder(LayoutInflater.from(context).inflate(R.layout.grid_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        String currentTopic = lst.get(position);
        holder.bindThisData(currentTopic);
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }
}
