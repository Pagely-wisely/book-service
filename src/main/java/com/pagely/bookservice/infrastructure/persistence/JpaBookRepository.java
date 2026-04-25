package com.pagely.bookservice.infrastructure.persistence;

import com.pagely.bookservice.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBookRepository extends JpaRepository<Book, String> {
}
