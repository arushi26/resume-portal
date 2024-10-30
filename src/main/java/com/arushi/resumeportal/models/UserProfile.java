package com.arushi.resumeportal.models;


import jakarta.persistence.*;

@Entity
@Table
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userName;
    private String theme;
    private String summary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
