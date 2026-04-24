package com.pagely.bookservice.application.service;

import com.pagely.bookservice.application.dto.command.CreateBookCommand;
import com.pagely.bookservice.application.dto.result.BookResult;
import com.pagely.bookservice.application.port.AladinProvider;
import com.pagely.bookservice.domain.model.Book;
import com.pagely.bookservice.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookCommandService {
    private final BookRepository bookRepository;
    private final AladinProvider aladinProvider;

    public BookResult createBook(CreateBookCommand command) {
        BookResult item = aladinProvider.getItem(command.getId());
        Book saved = bookRepository.save(
                Book.createBook(item.getId(), item.getTitle(), item.getAuthor(), item.getPublisher(),
                        item.getThumbnailUrl(), item.getDescription(), item.getPublishedAt(),
                        item.getCategoryId(), item.getCategoryName()));
        log.info("도서 생성");
        // TODO : 도서 통계 엔티티도 생성 해야 됨
        return BookResult.from(saved);
    }
}
