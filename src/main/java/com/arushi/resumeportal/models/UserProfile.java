package com.arushi.resumeportal.models;


import jakarta.persistence.*;

import java.util.*;

@Entity
@Table
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userName;
    private String theme;
    private String summary;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String designation;

    @ElementCollection
    @CollectionTable(name="user_skills", joinColumns = @JoinColumn(name="user_profile_id"))
    private final Map<String, Integer> skills = new HashMap<>();

    @OneToMany(cascade = CascadeType.ALL,
                orphanRemoval = true)
    @JoinColumn(name="job_id")
    private List<Job> jobs = new ArrayList<>();

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public Map<String, Integer> getSkills() {
        return Collections.unmodifiableMap(skills);
    }

    public void addSkills(String skill, Integer ratingOutOfTen) {
        if(ratingOutOfTen>10) ratingOutOfTen=10;
        if(ratingOutOfTen<0) ratingOutOfTen=0;

        this.skills.put(skill, ratingOutOfTen);
    }
}
