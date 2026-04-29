package com.pagely.bookservice.application.event;

import com.pagely.bookservice.domain.event.payload.BookCreatedEvent;
import com.pagely.bookservice.domain.event.payload.BookLikedEvent;
import com.pagely.bookservice.domain.event.payload.BookSearchedEvent;
import com.pagely.bookservice.domain.event.payload.BookUnlikedEvent;

public interface BookEventHandler {
    void handleBookCreated(BookCreatedEvent event);

    void handleBookLiked(BookLikedEvent event);

    void handleBookUnliked(BookUnlikedEvent event);

    void handleBookSearched(BookSearchedEvent event);
}
