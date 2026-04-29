package com.pagely.bookservice.domain.event;

import com.pagely.bookservice.domain.event.payload.BookCreatedEvent;
import com.pagely.bookservice.domain.event.payload.BookLikedEvent;
import com.pagely.bookservice.domain.event.payload.BookUnlikedEvent;

public interface BookEvents {
    void bookCreated(BookCreatedEvent event);

    void bookLiked(BookLikedEvent event);

    void bookUnliked(BookUnlikedEvent event);
}
