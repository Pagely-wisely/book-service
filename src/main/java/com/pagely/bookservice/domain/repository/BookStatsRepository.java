package com.pagely.bookservice.domain.repository;

import com.pagely.bookservice.domain.model.BookStats;
import java.util.List;
import java.util.Optional;

public interface BookStatsRepository {
    Optional<BookStats> findById(String bookId);

    List<BookStats> findByIds(List<String> bookIds);

    BookStats save(BookStats bookStats);
}
