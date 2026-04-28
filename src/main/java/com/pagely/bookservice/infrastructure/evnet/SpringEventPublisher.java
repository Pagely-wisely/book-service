package com.pagely.bookservice.infrastructure.evnet;

import com.pagely.bookservice.domain.event.BookEvents;
import com.pagely.bookservice.domain.event.payload.BookCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringEventPublisher implements BookEvents {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void bookCreated(BookCreatedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
