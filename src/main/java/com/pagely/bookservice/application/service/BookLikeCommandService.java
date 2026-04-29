package com.pagely.bookservice.application.service;

import com.pagely.bookservice.application.dto.command.CreateBookCommand;
import com.pagely.bookservice.application.dto.command.CreateBookLikeCommand;
import com.pagely.bookservice.application.dto.command.DeleteBookLikeCommand;
import com.pagely.bookservice.domain.event.BookEvents;
import com.pagely.bookservice.domain.exception.detail.DuplicatedBookLikeException;
import com.pagely.bookservice.domain.exception.detail.NotFoundLikeException;
import com.pagely.bookservice.domain.exception.detail.NotFoundStatsException;
import com.pagely.bookservice.domain.model.Book;
import com.pagely.bookservice.domain.model.BookLike;
import com.pagely.bookservice.domain.model.BookLike.BookLikeId;
import com.pagely.bookservice.domain.model.BookStats;
import com.pagely.bookservice.domain.repository.BookLikeRepository;
import com.pagely.bookservice.domain.repository.BookStatsRepository;
import com.pagely.bookservice.domain.service.BookLikeDeleteService;
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
    private final BookEvents bookEvents;

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
        BookLikeId bookLikeId = new BookLikeId(command.bookId(), command.userId());

        if (bookLikeRepository.existsById(bookLikeId)) {
            throw new DuplicatedBookLikeException();
        }

        Book book = bookGetOrCreateService.getOrCreateBookEntity(new CreateBookCommand(command.bookId()));

        bookLikeRepository.save(BookLike.create(book, command.userId(), bookEvents));

        BookStats bookStats = bookStatsRepository.findById(command.bookId())
                .orElseThrow(NotFoundStatsException::new);
        bookStats.increaseLikeCount();

        log.debug("도서 통계 좋아요 갯수 증가 bookId: {}", command.bookId());
        log.info("도서 좋아요 생성");
    }

    public void deleteBookLike(DeleteBookLikeCommand command) {
        BookLikeId bookLikeId = new BookLikeId(command.bookId(), command.userId());

        BookLike bookLike = bookLikeRepository.findById(bookLikeId)
                .orElseThrow(NotFoundLikeException::new);

        Book book = bookGetOrCreateService.getOrCreateBookEntity(new CreateBookCommand(command.bookId()));
        bookLike.hardDelete(book, bookLikeId, bookLikeDeleteService, bookEvents);

        BookStats bookStats = bookStatsRepository.findById(command.bookId())
                .orElseThrow(NotFoundStatsException::new);
        bookStats.decreaseLikeCount();

        log.debug("도서 통계 좋아요 갯수 감소 bookId: {}", command.bookId());
        log.info("도서 좋아요 삭제");
    }

}
