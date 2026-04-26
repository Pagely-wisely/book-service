package com.pagely.bookservice.domain.repository;

import com.pagely.bookservice.domain.model.BookLike;
import com.pagely.bookservice.domain.model.BookLike.BookLikeId;

public interface BookLikeRepository {
    boolean existsById(BookLikeId bookLikeId);

    BookLike save(BookLike bookLike);

    void deleteById(BookLikeId bookLikeId);
}
