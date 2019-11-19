package com.shamron.sm1x2.Adapter;
/*
 *****created by shamron on 11/7/2019 at 12:23 AM*****
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shamron.sm1x2.R;
import com.shamron.sm1x2.model.Tips;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TipsAdapter extends RecyclerView.Adapter<TipsViewHolder>{

    private Context context;
    private List<Tips> tipsList;

    private int green = R.color.green;
    private int orange = R.color.orange;
    private int red = R.color.red;

    public TipsAdapter(Context context, List<Tips> tipsList) {
        this.context = context;
        this.tipsList = tipsList;
    }

    @NonNull
    @Override
    public TipsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TipsViewHolder(
                LayoutInflater.from(context).inflate(R.layout.tip_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TipsViewHolder holder, int position) {
        Tips tips = tipsList.get(position);

        /*pass values to layout*/
        holder.league.setText(tips.getLeague());
        holder.kickoff.setText(tips.getKickoff());
        holder.teamA.setText(tips.getTeamA());
        holder.teamB.setText(tips.getTeamB());
        holder.prediction.setText(tips.getPrediction());
        holder.odds.setText(tips.getOdds());
        holder.txt_outcome.setText(tips.getOutcome());

        /*color txt as per outcome red:lost green:won orange:pending*/
        if (Objects.equals(tips.getOutcome(), "won")){
            holder.txt_outcome.setTextColor(context.getResources().getColor(green));
        }else if(Objects.equals(tips.getOutcome(), "lost")){
            holder.txt_outcome.setTextColor(context.getResources().getColor(red));
        }else  if(Objects.equals(tips.getOutcome(), "pending")){
            holder.txt_outcome.setTextColor(context.getResources().getColor(orange));
        }
    }

    @Override
    public int getItemCount() {
        return tipsList.size();
    }
}
