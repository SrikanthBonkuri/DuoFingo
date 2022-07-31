package com.example.duofingo;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DashBoardRankingViewHolder extends RecyclerView.ViewHolder {
    public TextView userName;
    public TextView countryRank;
    public TextView globalRank;
    public RelativeLayout dashBoardRankingListLayout;

    public DashBoardRankingViewHolder(@NonNull View itemView) {
        super(itemView);

        this.userName = itemView.findViewById(R.id.dashBoardRankingListUsername);
        this.countryRank = itemView.findViewById(R.id.dashBoardRankingListCountryRank);
        this.globalRank = itemView.findViewById(R.id.dashBoardRankingListGlobalRank);
        this.dashBoardRankingListLayout = itemView.findViewById(R.id.dashBoardRankingListLayout);
    }


    public void bindListData(DashBoardRankingDataSourceSet DashBoardRankingDataSource) {
        userName.setText(DashBoardRankingDataSource.getUserName());
        countryRank.setText(DashBoardRankingDataSource.getCountryRank());
        globalRank.setText(DashBoardRankingDataSource.getGlobalRank());
    }
}
