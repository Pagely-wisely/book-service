package com.pagely.bookservice.presentation.controller;

import com.pagely.bookservice.application.dto.command.CreateBookLikeCommand;
import com.pagely.bookservice.application.dto.command.DeleteBookLikeCommand;
import com.pagely.bookservice.application.dto.command.GetBookCommand;
import com.pagely.bookservice.application.dto.command.SearchBookCommand;
import com.pagely.bookservice.application.dto.result.BookDetailResult;
import com.pagely.bookservice.application.dto.result.BookSearchListResult;
import com.pagely.bookservice.application.service.BookLikeCommandService;
import com.pagely.bookservice.application.service.BookQueryService;
import com.pagely.common.pagination.PageRequest;
import com.pagely.common.pagination.PageResponse;
import com.pagely.common.response.ApiResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/books")
@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookLikeCommandService bookLikeCommandService;
    private final BookQueryService bookQueryService;

    @PostMapping("/{bookId}/like")
    public ResponseEntity<ApiResponse> addLike(
            @PathVariable String bookId,
            @RequestHeader("X-User-Id") UUID userId
    ) {
        bookLikeCommandService.createBookLike(new CreateBookLikeCommand(bookId, userId));
        return ApiResponse.created();
    }

    @DeleteMapping("/{bookId}/like")
    public ResponseEntity<ApiResponse> removeLike(
            @PathVariable String bookId,
            @RequestHeader("X-User-Id") UUID userId
    ) {
        bookLikeCommandService.deleteBookLike(new DeleteBookLikeCommand(bookId, userId));
        return ApiResponse.ok();
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchBooks(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestParam String query,
            @RequestParam(defaultValue = "Title") String queryType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size).toPageable();
        BookSearchListResult result = bookQueryService.searchBooks(
                new SearchBookCommand(userId, query, queryType, pageable)
        );
        return ApiResponse.ok(
                PageResponse.of(result.items(), pageable.getPageNumber(),
                        pageable.getPageSize(), result.totalResults())
        );
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse> getBook(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable String bookId
    ) {
        BookDetailResult result = bookQueryService.getBook(new GetBookCommand(bookId, userId));
        return ApiResponse.ok(result);
    }
}
