package com.xptostream.core.domain;

public record UserEvent(String userId, EventType eventType, String itemId) {
}