package com.shamron.sm1x2.Adapter;
/*
 *****created by shamron on 11/11/2019 at 09:04 PM*****
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamron.sm1x2.R;
import com.shamron.sm1x2.model.GoalScorers;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GoalScorerAdapter extends RecyclerView.Adapter<GoalScorerAdapter.GoalScorerViewHolder>{

    private Context context;
    private List<GoalScorers> goalScorersList;

    private int green = R.color.green;
    private int orange = R.color.orange;
    private int red = R.color.red;

    public GoalScorerAdapter(Context context, List<GoalScorers> goalScorersList) {
        this.context = context;
        this.goalScorersList = goalScorersList;
    }

    @NonNull
    @Override
    public GoalScorerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GoalScorerViewHolder(
                LayoutInflater.from(context).inflate(R.layout.goal_scorer_layout,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull GoalScorerViewHolder holder, int position) {
        GoalScorers goalScorers = goalScorersList.get(position);

        holder.teamA.setText(goalScorers.getTeamA());
        holder.teamB.setText(goalScorers.getTeamB());
        holder.league.setText(goalScorers.getLeague());
        holder.kickoff.setText(goalScorers.getKickoff());
        holder.prediction.setText(goalScorers.getPrediction());
        holder.txt_outcome.setText(goalScorers.getOutcome());

        /*color txt as per outcome red:lost green:won orange:pending*/
        if (Objects.equals(goalScorers.getOutcome(), "won")){
            holder.txt_outcome.setTextColor(context.getResources().getColor(green));
        }else if(Objects.equals(goalScorers.getOutcome(), "lost")){
            holder.txt_outcome.setTextColor(context.getResources().getColor(red));
        }else  if(Objects.equals(goalScorers.getOutcome(), "pending")){
            holder.txt_outcome.setTextColor(context.getResources().getColor(orange));
        }
    }

    @Override
    public int getItemCount() {
        return goalScorersList.size();
    }

    class GoalScorerViewHolder extends RecyclerView.ViewHolder {

        TextView teamA,teamB,prediction,kickoff,league,txt_outcome;

        public GoalScorerViewHolder(@NonNull View itemView) {
            super(itemView);
            teamA = itemView.findViewById(R.id.txt_gs_teamA);
            teamB = itemView.findViewById(R.id.txt_gs_teamB);
            prediction = itemView.findViewById(R.id.txt_gs_outcome);
            kickoff = itemView.findViewById(R.id.txt_gs_kickoff);
            league = itemView.findViewById(R.id.txt_gs_league);
            txt_outcome = itemView.findViewById(R.id.txt_gs_outcome);
        }
    }
}
