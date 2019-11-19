package com.shamron.sm1x2.model;
/*
 *****created by shamron on 11/7/2019 at 12:19 AM*****
 */

public class Tips {

    private String teamA,teamB,kickoff,prediction,odds,league,outcome;

    public Tips() {
    }

    public Tips(String teamA, String teamB, String kickoff, String prediction, String odds, String league, String outcome) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.kickoff = kickoff;
        this.prediction = prediction;
        this.odds = odds;
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

    public String getOdds() {
        return odds;
    }

    public String getLeague() {
        return league;
    }

    public String getOutcome() {
        return outcome;
    }
}
