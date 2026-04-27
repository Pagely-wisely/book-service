package com.pagely.bookservice.infrastructure.persistence;

import com.pagely.bookservice.domain.model.BookStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBookStatsRepository extends JpaRepository<BookStats, String> {
}
