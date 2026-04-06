package com.xptostream.core.port;

public interface UserInterestRepository {
    void saveIncremental(String userId, String category, long points);
}
