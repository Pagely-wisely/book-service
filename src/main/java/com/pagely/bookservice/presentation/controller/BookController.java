package com.pagely.bookservice.presentation.controller;

import com.pagely.bookservice.application.dto.command.CreateBookLikeCommand;
import com.pagely.bookservice.application.dto.command.DeleteBookLikeCommand;
import com.pagely.bookservice.application.service.BookLikeCommandService;
import com.pagely.common.response.ApiResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/books")
@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookLikeCommandService bookLikeCommandService;

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
}
