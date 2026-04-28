package com.pagely.bookservice.application.service;

import com.pagely.bookservice.application.dto.command.CreateBookCommand;
import com.pagely.bookservice.application.dto.result.BookResult;
import com.pagely.bookservice.application.port.AladinProvider;
import com.pagely.bookservice.domain.exception.detail.NotFoundBookException;
import com.pagely.bookservice.domain.model.Book;
import com.pagely.bookservice.domain.model.BookStats;
import com.pagely.bookservice.domain.repository.BookRepository;
import com.pagely.bookservice.domain.repository.BookStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookCommandService {
    private final BookRepository bookRepository;
    private final AladinProvider aladinProvider;
    private final BookStatsRepository bookStatsRepository;

    /**
     * <h1>도서 생성</h1>
     * <p>
     * 생성 주의점
     * <ul>
     *     <li>생성 호출이 여러 서비스에서 동시에 호출될 가능성이 있습니다.
     *     <li>isbn 을 key 값으로 생성하기 때문에 동시에 생성 요청 시, key값 충돌이 발생할 수 있습니다.
     * </ul>
     */
    public BookResult createBook(CreateBookCommand command) {
        BookResult item = aladinProvider.getItem(command.bookId());
        try {
            Book saved = bookRepository.save(
                    Book.create(item.getId(), item.getTitle(), item.getAuthor(), item.getPublisher(),
                            item.getThumbnailUrl(), item.getDescription(), item.getPublishedAt(),
                            item.getCategoryId(), item.getCategoryName()));

            bookStatsRepository.save(BookStats.builder()
                    .bookId(command.bookId())
                    .build());
            log.debug("도서 통계 생성 bookId: {}", command.bookId());

            log.info("도서 생성");
            return BookResult.from(saved);
        } catch (DataIntegrityViolationException e) {
            log.debug("동시 생성 요청으로 중복 발생. bookId: {}", command.bookId());
            return BookResult.from(bookRepository.findById(command.bookId())
                    .orElseThrow(NotFoundBookException::new));
        }
    }
}
