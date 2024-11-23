package com.Sportagram.sportagram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rates")
public class Rate {
    @Id
    @Column(name = "ratesID", nullable = false)
    private String ratesID;

    @Column(name = "userID")
    private String userID;

    @Column(name = "wins")
    private Integer wins;

    @Column(name = "loss")
    private Integer loss;

    @Column(name = "draw")
    private Integer draw;

    @Column(name = "match_cnt")
    private Integer matchCnt;

    @Column(name = "win_rates")
    private Float winRates;

    @Column(name = "loss_rates")
    private Float lossRates;

    @Column(name = "draw_rates")
    private Float drawRates;

    public String getRatesID() {
        return ratesID;
    }

    public void setRatesID(String ratesID) {
        this.ratesID = ratesID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLoss() {
        return loss;
    }

    public void setLoss(Integer loss) {
        this.loss = loss;
    }

    public Integer getDraw() { return draw; }

    public void setDraw(Integer draw) { this.draw = draw; }

    public Integer getMatchCnt() {
        return matchCnt;
    }

    public void setMatchCnt(Integer matchCnt) {
        this.matchCnt = matchCnt;
    }

    public Float getWinRates() {
        return winRates;
    }

    public void setWinRates(Float winRates) {
        this.winRates = winRates;
    }

    public Float getLossRates() {
        return lossRates;
    }

    public void setLossRates(Float lossRates) {
        this.lossRates = lossRates;
    }

    public Float getDrawRates() { return drawRates; }

    public void setDrawRates(Float drawRates) { this.drawRates = drawRates; }

}