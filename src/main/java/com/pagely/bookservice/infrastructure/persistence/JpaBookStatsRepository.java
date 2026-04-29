package com.pagely.bookservice.infrastructure.persistence;

import com.pagely.bookservice.domain.model.BookStats;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBookStatsRepository extends JpaRepository<BookStats, String> {
    List<BookStats> findByBookIdIn(List<String> bookIdList);
}
