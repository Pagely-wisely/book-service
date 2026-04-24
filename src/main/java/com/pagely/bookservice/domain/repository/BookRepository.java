package com.pagely.bookservice.domain.repository;

import com.pagely.bookservice.domain.model.Book;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(String id);
}
