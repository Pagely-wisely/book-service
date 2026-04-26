package com.pagely.bookservice.application.service;

import com.pagely.bookservice.application.dto.command.CreateBookCommand;
import com.pagely.bookservice.application.dto.command.CreateBookLikeCommand;
import com.pagely.bookservice.application.dto.command.DeleteBookLikeCommand;
import com.pagely.bookservice.application.dto.result.BookResult;
import com.pagely.bookservice.domain.model.BookLike;
import com.pagely.bookservice.domain.model.BookLike.BookLikeId;
import com.pagely.bookservice.infrastructure.persistence.BookLikeRepositoryAdapter;
import com.pagely.bookservice.infrastructure.persistence.BookRepositoryAdapter;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookLikeCommandService {
    private final BookGetOrCreateService bookGetOrCreateService;
    private final BookLikeRepositoryAdapter bookLikeRepositoryAdapter;
    private final BookRepositoryAdapter bookRepositoryAdapter;

    /*
     * 도서 좋아요 생성 메서드
     *
     * 도서 서비스는 유저가 상호작용한 도서 정보를 DB에 저장합니다.
     * 그러므로, 좋아요를 등록하는 시점에 도서 정보가 생성 됩니다.
     *
     * 불필요한 외부 api 요청을 줄이기 위해
     * 도서 정보를 생성하기 전에, 기존에 등록된 좋아요 여부를 먼저 체크합니다.
     */
    public void createBookLike(CreateBookLikeCommand command) {
        BookLikeId bookLikeId = new BookLikeId(command.getBookId(), command.getUserId());

        if (bookLikeRepositoryAdapter.existsById(bookLikeId)) {
            // TODO: 도메인 예외로 수정해야 됨
            throw new DuplicateRequestException(bookLikeId.toString());
        }

        BookResult book = bookGetOrCreateService.getOrCreateBook(CreateBookCommand.builder()
                .id(bookLikeId.getBookId())
                .build());
        bookLikeRepositoryAdapter.save(BookLike.builder()
                .bookId(bookLikeId.getBookId())
                .userId(bookLikeId.getUserId())
                .build());
        // TODO: BookStats increaseLike 추가 해야 됨
    }

    public void deleteBookLike(DeleteBookLikeCommand command) {
        BookLikeId bookLikeId = new BookLikeId(command.getBookId(), command.getUserId());

        if (!bookLikeRepositoryAdapter.existsById(bookLikeId)) {
            // TODO: 도메인 예외로 수정해야 됨
            throw new DuplicateRequestException(bookLikeId.toString());
        }
        bookLikeRepositoryAdapter.deleteById(bookLikeId);
        // TODO: BookStats decreaseLike 추가 해야 됨
    }

}
