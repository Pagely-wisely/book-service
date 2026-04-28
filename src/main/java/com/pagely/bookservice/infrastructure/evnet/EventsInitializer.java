package com.pagely.bookservice.infrastructure.evnet;

import com.pagely.bookservice.domain.event.DomainEventPublisher;
import com.pagely.bookservice.domain.event.Events;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventsInitializer {

    private final DomainEventPublisher eventPublisher;

    @PostConstruct
    void init() {
        Events.initialize(eventPublisher);
    }
}
