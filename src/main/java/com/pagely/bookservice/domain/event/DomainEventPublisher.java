package com.pagely.bookservice.domain.event;

public interface DomainEventPublisher {
    void publish(BaseEvent event);
}
