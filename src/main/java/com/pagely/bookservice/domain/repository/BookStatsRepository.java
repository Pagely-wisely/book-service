package com.pagely.bookservice.domain.repository;

import com.pagely.bookservice.domain.model.BookStats;
import java.util.Optional;

public interface BookStatsRepository {
    Optional<BookStats> findById(String bookId);

    BookStats save(BookStats bookStats);
}
