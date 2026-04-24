package com.pagely.bookservice.application.service;

import com.pagely.bookservice.application.dto.command.CreateBookCommand;
import com.pagely.bookservice.application.dto.result.BookResult;
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

    public BookResult getOrCreateBook(CreateBookCommand command) {
        return bookRepository.findById(command.getId())
                .map(BookResult::from)
                .orElseGet(
                        () -> {
                            log.debug("도서가 DB에 존재하지않아 외부 요청 후 도서 정보를 생성합니다. id: {}", command.getId());
                            return bookCommandService.createBook(command);
                        }
                );
    }
}
