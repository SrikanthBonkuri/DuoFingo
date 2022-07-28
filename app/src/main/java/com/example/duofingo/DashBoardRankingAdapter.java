package com.example.duofingo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DashBoardRankingAdapter extends RecyclerView.Adapter<DashBoardRankingViewHolder> {

    private ArrayList<DashBoardRankingDataSourceSet> dashBoardRankingDataSource;
    private final Context context;

    public DashBoardRankingAdapter(ArrayList<DashBoardRankingDataSourceSet> dashBoardRankingDataSource, Context context) {
        this.dashBoardRankingDataSource = dashBoardRankingDataSource;
        this.context = context;
    }


    @NonNull
    @Override
    public DashBoardRankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DashBoardRankingViewHolder(LayoutInflater.from(context).inflate(R.layout.dashboard_ranking_list,null));
    }

    @Override
    public void onBindViewHolder(@NonNull DashBoardRankingViewHolder holder, int position) {
        holder.bindListData(dashBoardRankingDataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return dashBoardRankingDataSource.size();
    }
}
