package com.shamron.sm1x2.Adapter;
/*
 *****created by shamron on 11/7/2019 at 12:36 AM*****
 */


import android.view.View;
import android.widget.TextView;

import com.shamron.sm1x2.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TipsViewHolder extends RecyclerView.ViewHolder
{
    TextView teamA,teamB,prediction,odds,kickoff,league,txt_outcome;

    public TipsViewHolder(@NonNull View itemView) {
        super(itemView);
        teamA = itemView.findViewById(R.id.txt_teamA);
        teamB = itemView.findViewById(R.id.txt_teamB);
        kickoff = itemView.findViewById(R.id.txt_kickoff);
        odds = itemView.findViewById(R.id.txt_odds);
        prediction = itemView.findViewById(R.id.txt_prediction);
        league = itemView.findViewById(R.id.txt_league);
        txt_outcome = itemView.findViewById(R.id.txt_outcome);
    }
}
