package com.pagely.bookservice.infrastructure.persistence;

import com.pagely.bookservice.domain.model.Book;
import com.pagely.bookservice.domain.repository.BookRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryAdapter implements BookRepository {
    private final JpaBookRepository jpaBookRepository;

    @Override
    public Book save(Book book) {
        return jpaBookRepository.save(book);
    }

    @Override
    public Optional<Book> findById(String id) {
        return jpaBookRepository.findById(id);
    }
}
