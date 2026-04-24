package com.pagely.bookservice.presentation.controller;

import com.pagely.bookservice.application.dto.command.CreateBookCommand;
import com.pagely.bookservice.application.dto.result.BookResult;
import com.pagely.bookservice.application.service.BookGeneratorService;
import com.pagely.bookservice.application.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/internal/books")
@RequiredArgsConstructor
public class InternalBookController {
    private final BookService bookService;
    private final BookGeneratorService bookGeneratorService;

    @PostMapping
    ResponseEntity<BookResult> getBook(
            @RequestBody CreateBookCommand request
    ) {
        BookResult result = bookService.getByInternalBook(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/generator")
    public ResponseEntity<String> triggerGenerator() {
        bookGeneratorService.generateBooksFromFile();
        return ResponseEntity.accepted().body("도서 데이터 생성 작업이 시작되었습니다.");
    }
}
