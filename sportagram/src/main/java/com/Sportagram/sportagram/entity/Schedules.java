package com.Sportagram.sportagram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Time;
import java.util.Date;

@Entity
@Table (name = "Schedules")
public class Schedules {

    @Id
    private String scheduleID;  // 고유 스케줄 ID (날짜 + 팀ID + 경기수)

    // 데이터 타입이 꼬여서 어노테이션 삭제
    // @ManyToOne
    // @JoinColumn(name = "teamID", referencedColumnName = "teamID", nullable = false)
    @Column(name = "teamID")
    private String team;  // 팀 정보 (홈)

    @Column(nullable = false)
    private int teamScore;  // 팀1의 점수 (홈)

    // @ManyToOne
    // @JoinColumn(name = "oppID", referencedColumnName = "teamID", nullable = false)
    @Column(name = "oppID")
    private String opponent;  // 상대 팀 정보 (원정)

    @Column(nullable = false)
    private int oppScore;  // 팀2의 점수 (원정)

    @Column(nullable = false)
    private Date matchDate;  // 경기 날짜

    @Column(nullable = false)
    private Time matchTime;  // 경기 시간 (ex: 14:00)

    @Column(nullable = false)
    private String stadium;  // 경기장 (ex: 잠실)

    @Column(nullable = true)
    private String matchStatus;  // 경기 상태

    // 기본 생성자
    public Schedules() {
    }


    // Getter와 Setter

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getTeamScore() {
        return teamScore;
    }

    public void setTeamScore(int teamScore) {
        this.teamScore = teamScore;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public int getOppScore() {
        return oppScore;
    }

    public void setOppScore(int oppScore) {
        this.oppScore = oppScore;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public Time getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Time matchTime) {
        this.matchTime = matchTime;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

}
