package com.pagely.bookservice.infrastructure.persistence;

import com.pagely.bookservice.domain.model.BookLike;
import com.pagely.bookservice.domain.model.BookLike.BookLikeId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBookLikeRepository extends JpaRepository<BookLike, BookLikeId> {
    Optional<BookLike> findByBookId(String bookId);
}
