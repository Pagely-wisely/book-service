package com.pagely.bookservice.domain.event;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
public abstract class BaseEvent {
    private final String eventId;
    private final String eventType;
    private final String domainType;
    private final String domainId;
    private final Instant occurredAt;

    protected BaseEvent(String domainType, String domainId) {
        this.eventId = UUID.randomUUID().toString();
        this.eventType = this.getClass().getSimpleName();
        this.domainType = domainType;
        this.domainId = domainId;
        this.occurredAt = Instant.now();
    }
}
