package com.example.ems.models;

import java.time.LocalDate;

public class Mood {
    private int moodId;
    private int userId;
    private int moodRating; // Rating from 1 to 5
    private LocalDate date;

    public Mood(int moodId, int  userId, int moodRating, LocalDate date) {
        this.moodId = moodId;
        this.userId = userId;
        this.moodRating = moodRating;
        this.date = date;
    }

    public int getMoodId() {return moodId;}
    public void setMoodId(int moodId) {this.moodId = moodId;}

    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}

    public int getMoodRating() {return moodRating;}

    public void setMoodRating(int moodRating) {this.moodRating = moodRating;}

    public LocalDate getDate() {return date;}
    public void setDate(LocalDate date) {this.date = date;}
}