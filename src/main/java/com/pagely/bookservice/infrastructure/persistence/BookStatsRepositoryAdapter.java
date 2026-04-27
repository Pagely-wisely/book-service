package com.pagely.bookservice.infrastructure.persistence;

import com.pagely.bookservice.domain.model.BookStats;
import com.pagely.bookservice.domain.repository.BookStatsRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookStatsRepositoryAdapter implements BookStatsRepository {
    private final JpaBookStatsRepository jpaBookStatsRepository;

    @Override
    public Optional<BookStats> findById(String bookId) {
        return jpaBookStatsRepository.findById(bookId);
    }

    @Override
    public BookStats save(BookStats bookStats) {
        return jpaBookStatsRepository.save(bookStats);
    }
}
