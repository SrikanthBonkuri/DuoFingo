package com.example.duofingo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterViewHolder>{

    // the list of pairs
    private final ArrayList<String> lst;

    // the context
    private final Context context;

    String currentChapter;

    public ChapterAdapter(ArrayList<String> lst, Context context) {
        this.lst = lst;
        this.context = context;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChapterViewHolder(LayoutInflater.from(context).inflate(R.layout.slab_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        String currentChapter = lst.get(position);

        holder.cardRelativeLayout.setOnClickListener(v -> {
            Toast.makeText(context.getApplicationContext(), "Opening" + holder.actualChapterName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context.getApplicationContext(), SpecificChapterActivity.class);
            intent.putExtra("chapter", holder.actualChapterName);
            context.startActivity(intent);
        });

        holder.bindThisData(currentChapter);
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }
}
