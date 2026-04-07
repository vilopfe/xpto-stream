package com.xptostream.core.domain;

public enum EventType {
    PURCHASE(15), VIEW(1), LIKE(3);

    private final long points;

    EventType(long points) {
        this.points = points;
    }

    public long getPoints() {
        return points;
    }
}
