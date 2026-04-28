package com.pagely.bookservice.application.port.out;

import com.pagely.bookservice.domain.event.payload.BookLikedEvent;
import com.pagely.bookservice.domain.event.payload.BookUnlikedEvent;

public interface BookLikeEventPort {
    void publishBookLiked(BookLikedEvent event);

    void publishBookUnliked(BookUnlikedEvent event);
}
