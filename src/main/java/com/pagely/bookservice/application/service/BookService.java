package com.pagely.bookservice.application.service;

import com.pagely.bookservice.application.dto.command.CreateBookCommand;
import com.pagely.bookservice.application.dto.result.BookResult;
import com.pagely.bookservice.domain.model.Book;
import com.pagely.bookservice.domain.repository.BookRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookCommandService bookCommandService;
    private final BookRepository bookRepository;

    public BookResult getByInternalBook(CreateBookCommand command) {
        Optional<Book> optionalBook = bookRepository.findById(command.getId());
        if (optionalBook.isPresent()) {
            log.debug("DB에서 조회 id: {}", command.getId());
            return BookResult.from(optionalBook.get());
        }

        log.debug("도서가 DB에 존재하지않아 외부 요청 후 도서 정보를 생성합니다. id: {}", command.getId());
        BookResult saved = bookCommandService.createBook(CreateBookCommand.builder()
                .id(command.getId())
                .build());
        return saved;
    }
}
