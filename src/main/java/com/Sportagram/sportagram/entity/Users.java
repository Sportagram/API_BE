package com.Sportagram.sportagram.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID", nullable = false, length = 50)
    private Long userID;

    @Column(name = "userName", nullable = false, length = 50)
    private String userName;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "nick_name", nullable = false, length = 50)
    private String nickName = "DefaultNickName";

    //@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "my_team", nullable = false)
    private String myTeam = "DefaultTeam";

    public String getMyTeam() {
        return myTeam;
    }

    public void setMyTeam(String myTeam) {
        this.myTeam = myTeam;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
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