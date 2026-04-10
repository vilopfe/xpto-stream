package com.xptostream.core.domain;

import java.util.HashMap;
import java.util.Map;

public class UserInterest {
    private String userId;
    private Map<String, Integer> interest = new HashMap<>();
    private int totalPoints = 0;

    public UserInterest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, Integer> getinterest() {
        return interest;
    }

    public void setinterest(Map<String, Integer> interest) {
        this.interest = interest;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void addPoints(int points) {
        this.totalPoints += points;
    }
}