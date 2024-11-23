package com.Sportagram.sportagram.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "Team")
public class Team {

    @Id
    private String teamID;

    @Column (name = "team_name")
    private String teamName;

    @Column (name = "short_name")
    private String shortName;

    @Column (name = "home_stadium")
    private String homeStadium;

    // 생성자, getter, setter
    public Team(String teamID, String teamName, String shortName,String homeStadium) {
        this.teamID = teamID;
        this.teamName = teamName;
        this.shortName = shortName;
        this.homeStadium = homeStadium;
    }

    public Team() {

    }

    // public Team() {}

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

    @Override
    public String toString() {
        return "Team{" +
                "teamID='" + teamID + '\'' +
                ", teamName='" + teamName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", stadium='" + homeStadium + '\'' +
                '}';
    }
}
