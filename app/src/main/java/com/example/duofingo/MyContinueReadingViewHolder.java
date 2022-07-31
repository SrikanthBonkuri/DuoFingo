package com.example.duofingo;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyContinueReadingViewHolder extends RecyclerView.ViewHolder{

    TextView completionStatus;
    public RelativeLayout continueReadingLayout;

    public MyContinueReadingViewHolder(@NonNull View itemView) {
        super(itemView);
        completionStatus = itemView.findViewById(R.id.continueReadingCompletion);
        continueReadingLayout = itemView.findViewById(R.id.continueReadingLayout);
    }

    public void bindListData(ContinueReadingDataSource status) {
        completionStatus.setText(status.getChapterName());
    }

}
