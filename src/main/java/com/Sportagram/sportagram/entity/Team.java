package com.Sportagram.sportagram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "team") // 테이블 명 소문자로 통일
public class Team {

    @Id
    @Column(name = "teamID")
    private String teamID;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "home_stadium")
    private String homeStadium;

    @Column(name = "eng_name")  // 영어 이름 필드 추가
    private String engName;

    // 생성자
    public Team(String teamID, String teamName, String shortName, String homeStadium, String engName) {
        this.teamID = teamID;
        this.teamName = teamName;
        this.shortName = shortName;
        this.homeStadium = homeStadium;
        this.engName = engName;
    }

    public Team() {
    }

    // Getter and Setter methods
    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getHomeStadium() {
        return homeStadium;
    }

    public void setHomeStadium(String homeStadium) {
        this.homeStadium = homeStadium;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamID='" + teamID + '\'' +
                ", teamName='" + teamName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", homeStadium='" + homeStadium + '\'' +
                ", engName='" + engName + '\'' +
                '}';
    }
}
