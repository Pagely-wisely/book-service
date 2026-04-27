package com.pagely.bookservice.infrastructure.persistence;

import com.pagely.bookservice.domain.model.BookLike;
import com.pagely.bookservice.domain.model.BookLike.BookLikeId;
import com.pagely.bookservice.domain.repository.BookLikeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookLikeRepositoryAdapter implements BookLikeRepository {
    private final JpaBookLikeRepository jpaBookLikeRepository;

    @Override
    public boolean existsById(BookLikeId bookLikeId) {
        return jpaBookLikeRepository.existsById(bookLikeId);
    }

    @Override
    public BookLike save(BookLike bookLike) {
        return jpaBookLikeRepository.save(bookLike);
    }

    @Override
    public void deleteById(BookLikeId bookLikeId) {
        jpaBookLikeRepository.deleteById(bookLikeId);
    }

    @Override
    public Optional<BookLike> findById(BookLikeId bookLikeId) {
        return jpaBookLikeRepository.findById(bookLikeId);
    }

}
