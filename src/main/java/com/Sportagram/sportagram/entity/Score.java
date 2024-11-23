package com.Sportagram.sportagram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Records")
public class Score {
    @Id
    @Column(name = "recordID")
    private String recordID;

    @Column(name = "scheduleID")
    private String scheduleID;

    @Column(name = "away_score")
    private String awayScore;

    @Column(name = "home_score")
    private String homeScore;

    @Column(name = "away_records")
    private String awayRecords;

    @Column(name = "home_records")
    private String homeRecords;

    @Column(name = "game_records")
    private String gameRecords;

    @Column(name = "away_pitchers")
    private String awayPitchers;

    @Column(name = "home_pitchers")
    private String homePitchers;

    public Score() {}

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getSchedules() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(String awayScore) {
        this.awayScore = awayScore;
    }

    public String getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(String homeScore) {
        this.homeScore = homeScore;
    }

    public String getAwayRecords() {
        return awayRecords;
    }

    public void setAwayRecords(String awayRecords) {
        this.awayRecords = awayRecords;
    }

    public String getHomeRecords() {
        return homeRecords;
    }

    public void setHomeRecords(String homeRecords) {
        this.homeRecords = homeRecords;
    }

    public String getGameRecords() {
        return gameRecords;
    }

    public void setGameRecords(String gameRecords) {
        this.gameRecords = gameRecords;
    }

    public String getAwayPitchers() {
        return awayPitchers;
    }

    public void setAwayPitchers(String awayPitchers) {
        this.awayPitchers = awayPitchers;
    }

    public String getHomePitchers() {
        return homePitchers;
    }

    public void setHomePitchers(String homePitchers) {
        this.homePitchers = homePitchers;
    }
}

