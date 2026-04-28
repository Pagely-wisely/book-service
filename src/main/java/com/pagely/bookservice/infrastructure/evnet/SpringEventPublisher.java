package com.pagely.bookservice.infrastructure.evnet;

import com.pagely.bookservice.domain.event.BaseEvent;
import com.pagely.bookservice.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(BaseEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
