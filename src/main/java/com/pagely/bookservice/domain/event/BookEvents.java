package com.pagely.bookservice.domain.event;

import com.pagely.bookservice.domain.event.payload.BookCreatedEvent;

public interface BookEvents {
    void bookCreated(BookCreatedEvent event);
}
