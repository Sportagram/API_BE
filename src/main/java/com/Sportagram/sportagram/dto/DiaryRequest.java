package com.Sportagram.sportagram.dto;

public class DiaryRequest {
    private String year;
    private String month;
    private String day;
    private String time;
    private String stadium;
    private String comment;
    private String userID;

    public DiaryRequest() {}

    public DiaryRequest(String year, String month, String day, String stadium, String comment, String userID) {
        this.year = year;
        this.month = month;
        this.day = day;
        //this.time = time;
        this.stadium = stadium;
        this.comment = comment;
        this.userID = userID;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    /*
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

     */

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "DiaryRequest{" +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", stadium='" + stadium + '\'' +
                ", comment='" + comment + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}
