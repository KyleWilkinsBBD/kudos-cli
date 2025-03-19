package com.bbdgrads.kudos_cli.model;

import org.springframework.stereotype.Component;

@Component
public class UserSession {

    private String googleId;
    private String username;
    private String teamName;
    private Long userId;
    private boolean isAdmin;

    public UserSession(String googleId, String username, String teamName, Long userId, boolean isAdmin) {
        this.googleId = googleId;
        this.username = username;
        this.teamName = teamName;
        this.userId = userId;
        this.isAdmin = isAdmin;
    }

    public UserSession(){}

    public String getGoogleId() {
        return googleId;
    }

    public String getUsername() {
        return username;
    }

    public String getTeamName() {
        return teamName;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "googleId='" + googleId + '\'' +
                ", username='" + username + '\'' +
                ", teamName='" + teamName + '\'' +
                ", userId=" + userId +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
