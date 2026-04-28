package com.pagely.bookservice.application.port.out;

import com.pagely.bookservice.domain.event.payload.BookCreatedEvent;

public interface BookEventPort {
    void publishBookCreated(BookCreatedEvent event);
}
