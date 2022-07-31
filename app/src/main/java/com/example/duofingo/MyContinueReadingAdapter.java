package com.example.duofingo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyContinueReadingAdapter extends RecyclerView.Adapter<MyContinueReadingViewHolder> {

    ArrayList<String> continueReadingDataSource;
    final Context context;

    public MyContinueReadingAdapter(ArrayList<String> continueReadingDataSource, Context context) {
        this.continueReadingDataSource = continueReadingDataSource;
        this.context = context;
    }
    @NonNull
    @Override
    public MyContinueReadingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyContinueReadingViewHolder(LayoutInflater.from(context).inflate(R.layout.continue_reading_list,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyContinueReadingViewHolder holder, int position) {
        holder.bindListData(continueReadingDataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return continueReadingDataSource.size();
    }
}
