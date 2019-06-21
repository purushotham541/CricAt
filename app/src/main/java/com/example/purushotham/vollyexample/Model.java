package com.example.purushotham.vollyexample;

public class Model
{
    String uniqid;
    String team1;
    String team2;
    String date;
    String match_status;
    String match_type;

    public Model(String uniqid, String team1, String team2, String date, String match_status, String match_type) {
        this.uniqid = uniqid;
        this.team1 = team1;
        this.team2 = team2;
        this.date = date;
        this.match_status = match_status;
        this.match_type = match_type;
    }

    public String getUniqid() {
        return uniqid;
    }

    public void setUniqid(String uniqid) {
        this.uniqid = uniqid;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMatch_status() {
        return match_status;
    }

    public void setMatch_status(String match_status) {
        this.match_status = match_status;
    }

    public String getMatch_type() {
        return match_type;
    }

    public void setMatch_type(String match_type) {
        this.match_type = match_type;
    }
}
