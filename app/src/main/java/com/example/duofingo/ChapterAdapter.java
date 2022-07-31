package com.example.duofingo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterViewHolder>{

    // the list of pairs
    private final ArrayList<String> lst;

    // the context
    private final Context context;

    public ChapterAdapter(ArrayList<String> lst, Context context) {
        this.lst = lst;
        this.context = context;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChapterViewHolder(LayoutInflater.from(context).inflate(R.layout.grid_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        String currentChapter = lst.get(position);
        holder.bindThisData(currentChapter);
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }
}
