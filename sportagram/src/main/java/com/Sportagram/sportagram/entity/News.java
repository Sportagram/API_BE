package com.Sportagram.sportagram.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "newsID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamID")
    private Team teamID;

    @Column(name = "title", length = 30)
    private String title;

    @Column(name = "url", length = 100)
    private String url;

    @Column(name = "newsDate")
    private Instant newsDate;

    @Column(name = "teamName")
    private String teamName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Team getTeamID() {
        return teamID;
    }

    public void setTeamID(Team teamID) {
        this.teamID = teamID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Instant getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Instant newsDate) {
        this.newsDate = newsDate;
    }

    public String getTeamName(){return teamName;}
    public void setTeamName(String teamName){this.teamName= teamName;}
}