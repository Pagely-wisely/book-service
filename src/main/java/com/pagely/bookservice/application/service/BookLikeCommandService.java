package com.pagely.bookservice.application.service;

import com.pagely.bookservice.application.dto.command.CreateBookCommand;
import com.pagely.bookservice.application.dto.command.CreateBookLikeCommand;
import com.pagely.bookservice.application.dto.command.DeleteBookLikeCommand;
import com.pagely.bookservice.application.dto.result.BookResult;
import com.pagely.bookservice.domain.model.BookLike;
import com.pagely.bookservice.domain.model.BookLike.BookLikeId;
import com.pagely.bookservice.domain.model.BookStats;
import com.pagely.bookservice.domain.repository.BookLikeRepository;
import com.pagely.bookservice.domain.repository.BookStatsRepository;
import com.pagely.bookservice.domain.service.BookLikeDeleteService;
import com.sun.jdi.request.DuplicateRequestException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookLikeCommandService {
    private final BookGetOrCreateService bookGetOrCreateService;
    private final BookLikeRepository bookLikeRepository;
    private final BookStatsRepository bookStatsRepository;
    private final BookLikeDeleteService bookLikeDeleteService;

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

        if (bookLikeRepository.existsById(bookLikeId)) {
            // TODO: 도메인 예외로 수정해야 됨
            throw new DuplicateRequestException(bookLikeId.toString());
        }

        BookResult book = bookGetOrCreateService.getOrCreateBook(CreateBookCommand.builder()
                .id(bookLikeId.getBookId())
                .build());
        bookLikeRepository.save(BookLike.builder()
                .bookId(bookLikeId.getBookId())
                .userId(bookLikeId.getUserId())
                .build());

        BookStats bookStats = bookStatsRepository.findById(command.getBookId())
                // TODO: 도메인 예외로 수정해야 됨
                .orElseThrow(NoSuchElementException::new);
        bookStats.increaseLikeCount();

        log.debug("도서 통계 좋아요 갯수 증가 id: {}", command.getBookId());
        log.info("도서 좋아요 생성");
    }

    public void deleteBookLike(DeleteBookLikeCommand command) {
        BookLikeId bookLikeId = new BookLikeId(command.getBookId(), command.getUserId());

        BookLike bookLike = bookLikeRepository.findByBookId(command.getBookId())
                .orElseThrow(NoSuchElementException::new);

        bookLike.hardDelete(command.getUserId(), bookLikeDeleteService);

        BookStats bookStats = bookStatsRepository.findById(command.getBookId())
                // TODO: 도메인 예외로 수정해야 됨
                .orElseThrow(NoSuchElementException::new);
        bookStats.decreaseLikeCount();

        log.debug("도서 통계 좋아요 갯수 감소 id: {}", command.getBookId());
        log.info("도서 좋아요 삭제");
    }

}
