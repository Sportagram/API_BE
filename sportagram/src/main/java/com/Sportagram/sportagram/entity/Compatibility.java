package com.Sportagram.sportagram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Compatibility")
public class Compatibility {
    @Id
    @Column(name = "compatibilityID", nullable = false)
    private String compatID;

    @Column(name = "userID")
    private String userID;

    @Column(name = "player_name", length = 20)
    private String playerName;

    @Column(name = "win_cnt")
    private Integer winCnt;

    @Column(name = "loss_cnt")
    private Integer lossCnt;

    @Column(name = "match_cnt")
    private Integer matchCnt;

    @Column(name = "win_rates")
    private Float winRates;

    @Column(name = "loss_rates")
    private Float lossRates;

    @Column(name = "draw_cnt")
    private Integer drawCnt;

    @Column(name = "draw_rates")
    private Float drawRates;

    public Float getDrawRates() {
        return drawRates;
    }

    public void setDrawRates(Float drawRates) {
        this.drawRates = drawRates;
    }

    public Integer getDrawCnt() {
        return drawCnt;
    }

    public void setDrawCnt(Integer drawCnt) {
        this.drawCnt = drawCnt;
    }

    public Float getLossRates() {
        return lossRates;
    }

    public void setLossRates(Float lossRates) {
        this.lossRates = lossRates;
    }

    public Float getWinRates() {
        return winRates;
    }

    public void setWinRates(Float winRates) {
        this.winRates = winRates;
    }

    public Integer getMatchCnt() {
        return matchCnt;
    }

    public void setMatchCnt(Integer matchCnt) {
        this.matchCnt = matchCnt;
    }

    public String getCompatID() {
        return compatID;
    }

    public void setCompatID(String compatID) {
        this.compatID = compatID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getWinCnt() {
        return winCnt;
    }

    public void setWinCnt(Integer winCnt) {
        this.winCnt = winCnt;
    }

    public Integer getLossCnt() {
        return lossCnt;
    }

    public void setLossCnt(Integer lossCnt) {
        this.lossCnt = lossCnt;
    }

}