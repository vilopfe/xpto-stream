package com.xptostream.core.domain;

import java.util.HashMap;
import java.util.Map;

public class UserInterest {
    private String userId;
    private Map<String, Integer> interests = new HashMap<>();
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

    public Map<String, Integer> getInterests() {
        return interests;
    }

    public void setInterests(Map<String, Integer> interests) {
        this.interests = interests;
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