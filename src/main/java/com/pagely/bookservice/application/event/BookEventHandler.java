package com.pagely.bookservice.application.event;

import com.pagely.bookservice.domain.event.payload.BookCreatedEvent;

public interface BookEventHandler {
    void handleBookCreated(BookCreatedEvent event);
}
