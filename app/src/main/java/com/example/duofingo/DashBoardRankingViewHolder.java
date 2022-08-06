package com.example.duofingo;

import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class DashBoardRankingViewHolder extends RecyclerView.ViewHolder {
    public TextView userName;
    public TextView countryRank;
    public TextView country;
    public RelativeLayout dashBoardRankingListLayout;

    public DashBoardRankingViewHolder(@NonNull View itemView) {
        super(itemView);

        this.userName = itemView.findViewById(R.id.dashBoardRankingListUsername);
        this.countryRank = itemView.findViewById(R.id.dashBoardRankingListCountryRank);
        this.country = itemView.findViewById(R.id.dashBoardRankingListGlobalRank);
        this.dashBoardRankingListLayout = itemView.findViewById(R.id.dashBoardRankingListLayout);
    }


    public void bindListData(DashBoardRankingDataSourceSet DashBoardRankingDataSource, String myUserName) {
        if(Objects.equals(DashBoardRankingDataSource.getUserName(), myUserName)) {
            userName.setTextColor(Color.RED);
            countryRank.setTextColor(Color.RED);
            country.setTextColor(Color.RED);
        }
        this.userName.setText(DashBoardRankingDataSource.getUserName());
        countryRank.setText(DashBoardRankingDataSource.getRank());
        country.setText(DashBoardRankingDataSource.getCountry());
    }
}
