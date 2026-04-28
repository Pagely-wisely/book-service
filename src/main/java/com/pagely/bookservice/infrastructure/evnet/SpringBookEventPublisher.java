package com.pagely.bookservice.infrastructure.evnet;

import com.pagely.bookservice.domain.event.BookEvents;
import com.pagely.bookservice.domain.event.payload.BookCreatedEvent;
import com.pagely.bookservice.domain.event.payload.BookLikedEvent;
import com.pagely.bookservice.domain.event.payload.BookUnlikedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringBookEventPublisher implements BookEvents {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void bookCreated(BookCreatedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void bookLiked(BookLikedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void bookUnliked(BookUnlikedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
