package com.pagely.bookservice.infrastructure.persistence;

import com.pagely.bookservice.domain.model.BookLike;
import com.pagely.bookservice.domain.model.BookLike.BookLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBookLikeRepository extends JpaRepository<BookLike, BookLikeId> {
}
