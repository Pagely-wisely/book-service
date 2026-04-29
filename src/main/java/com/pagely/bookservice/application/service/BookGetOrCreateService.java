package com.pagely.bookservice.application.service;

import com.pagely.bookservice.application.dto.command.CreateBookCommand;
import com.pagely.bookservice.application.dto.result.BookResult;
import com.pagely.bookservice.domain.model.Book;
import com.pagely.bookservice.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookGetOrCreateService {
    private final BookCommandService bookCommandService;
    private final BookRepository bookRepository;

    // API 응답용
    public BookResult getOrCreateBook(CreateBookCommand command) {
        return bookRepository.findById(command.bookId())
                .map(BookResult::from)
                .orElseGet(() -> {
                    log.debug("도서가 DB에 존재하지않아 외부 요청 후 도서 정보를 생성합니다. bookId: {}", command.bookId());
                    return bookCommandService.createBook(command);
                });
    }

    // 내부 로직용 — Book 엔티티 반환
    public Book getOrCreateBookEntity(CreateBookCommand command) {
        return bookRepository.findById(command.bookId())
                .orElseGet(() -> {
                    log.debug("도서가 DB에 존재하지않아 외부 요청 후 도서 정보를 생성합니다. bookId: {}", command.bookId());
                    return bookCommandService.createBookEntity(command);
                });
    }
}
