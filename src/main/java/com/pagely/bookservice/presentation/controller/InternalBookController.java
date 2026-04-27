package com.pagely.bookservice.presentation.controller;

import com.pagely.bookservice.application.dto.command.CreateBookCommand;
import com.pagely.bookservice.application.dto.result.BookResult;
import com.pagely.bookservice.application.service.BookGeneratorService;
import com.pagely.bookservice.application.service.BookGetOrCreateService;
import com.pagely.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/internal/books")
@RequiredArgsConstructor
public class InternalBookController {
    private final BookGetOrCreateService bookService;
    private final BookGeneratorService bookGeneratorService;

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse> getBook(
            @PathVariable String bookId
    ) {
        BookResult result = bookService.getOrCreateBook(new CreateBookCommand(bookId));
        // TODO: internal api 반환 형식에 따라 presentation 레이어 DTO 추가
        return ApiResponse.ok(result);
    }

    @PostMapping("/generator")
    public ResponseEntity<ApiResponse> triggerGenerator() {
        bookGeneratorService.generateBooksFromFile();
        // TODO: ACCEPT 같은 비동기 처리 응답 상태 필요
        return ApiResponse.ok();
    }
}
