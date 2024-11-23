package com.Sportagram.sportagram.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "diary")
public class Diary {
    @Id
    @Column(name = "diaryID", nullable = false)
    private String diaryID;

    @Column(name = "userID")
    private String userID;

    @Column(name = "scheduleID")
    private String scheduleID;

    @Column(name = "match_cnt")
    private Integer matchCnt;

    @Lob
    @Column(name = "comments")
    private String comments;

    @Lob
    @Column(name = "game_result")
    private String gameResult;

    public String getDiaryID() {
        return diaryID;
    }

    public void setDiaryID(String id) {
        this.diaryID = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Integer getMatchCnt() {
        return matchCnt;
    }

    public void setMatchCnt(Integer matchCnt) {
        this.matchCnt = matchCnt;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getGameResult() {
        return gameResult;
    }

    public void setGameResult(String gameResult) {
        this.gameResult = gameResult;
    }

}