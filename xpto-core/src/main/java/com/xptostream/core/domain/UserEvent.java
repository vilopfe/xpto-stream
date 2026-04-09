package com.xptostream.core.domain;

public record UserEvent
    (   
        String userId, 
        String category, 
        EventType eventType
    ) {}