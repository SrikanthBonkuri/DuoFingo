package com.example.duofingo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyContinueReadingViewHolder extends RecyclerView.ViewHolder{

    TextView completionStatus;

    public MyContinueReadingViewHolder(@NonNull View itemView) {
        super(itemView);
        completionStatus = itemView.findViewById(R.id.continueReadingCompletion);
    }

    public void bindListData(String status) {
        completionStatus.setText(status);
    }

}
