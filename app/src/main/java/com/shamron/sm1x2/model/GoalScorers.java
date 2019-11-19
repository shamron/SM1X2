package com.shamron.sm1x2.model;
/*
 *****created by shamron on 11/11/2019 at 08:04 PM*****
 */

public class GoalScorers {

    private String teamA,teamB,kickoff,prediction,league,outcome;

    public GoalScorers() {
    }

    public GoalScorers(String teamA, String teamB, String kickoff, String prediction, String league, String outcome) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.kickoff = kickoff;
        this.prediction = prediction;
        this.league = league;
        this.outcome = outcome;
    }

    public String getTeamA() {
        return teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public String getKickoff() {
        return kickoff;
    }

    public String getPrediction() {
        return prediction;
    }

    public String getLeague() {
        return league;
    }

    public String getOutcome() {
        return outcome;
    }
}
