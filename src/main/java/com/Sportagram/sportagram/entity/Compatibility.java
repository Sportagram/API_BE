package com.Sportagram.sportagram.entity;

import jakarta.persistence.*;

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

    @Column(name = "sp_wins")
    private Integer spWins;

    @Column(name = "sp_loss")
    private Integer spLoss;

    @Column(name = "homeruns")
    private Integer homeruns;

    @Column(name = "out_count")
    private Integer outCount;

    @Column(name = "hits")
    private Integer hits;

    @Column(name = "bb")
    private Integer bb;

    @Column(name = "k")
    private Integer k;

    @Column(name = "runs")
    private Integer runs;

    @Column(name = "er")
    private Integer er;

    public Integer getSPwins() {
        return spWins;
    }

    public void setSPwins(Integer spWins) {
        this.spWins = spWins;
    }

    public Integer getSPloss() {
        return spLoss;
    }

    public void setSPloss(Integer spLoss) {
        this.spLoss = spLoss;
    }

    public Integer getHomeruns() {
        return homeruns;
    }

    public void setHomeruns(Integer homeruns) {
        this.homeruns = homeruns;
    }

    public Integer getEr() {
        return er;
    }

    public void setEr(Integer er) {
        this.er = er;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public Integer getBb() {
        return bb;
    }

    public void setBb(Integer bb) {
        this.bb = bb;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getOutCount() {
        return outCount;
    }

    public void setOutCount(Integer outCount) {
        this.outCount = outCount;
    }


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