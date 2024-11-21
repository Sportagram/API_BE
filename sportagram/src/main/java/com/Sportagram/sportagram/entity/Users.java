package com.Sportagram.sportagram.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "userID", nullable = false, length = 20)
    private String userID;

    @Column(name = "userName", nullable = false, length = 20)
    private String userName;

    @Column(name = "email", nullable = false, length = 20)
    private String email;

    @Column(name = "nick_name", nullable = false, length = 20)
    private String nickName;

    //@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "my_team", nullable = false)
    private String myTeam;

    public String getMyTeam() {
        return myTeam;
    }

    public void setMyTeam(String myTeam) {
        this.myTeam = myTeam;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

}