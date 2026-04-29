package com.pagely.bookservice.application.service;

import com.pagely.bookservice.application.dto.command.GetBookCommand;
import com.pagely.bookservice.application.dto.command.SearchBookCommand;
import com.pagely.bookservice.application.dto.result.BookDetailResult;
import com.pagely.bookservice.application.dto.result.BookResult;
import com.pagely.bookservice.application.dto.result.BookSearchListResult;
import com.pagely.bookservice.application.dto.result.BookSummaryResult;
import com.pagely.bookservice.application.port.out.AladinProvider;
import com.pagely.bookservice.domain.event.BookEvents;
import com.pagely.bookservice.domain.event.payload.BookSearchedEvent;
import com.pagely.bookservice.domain.model.BookStats;
import com.pagely.bookservice.domain.repository.BookStatsRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookQueryService {
    private final AladinProvider aladinProvider;
    private final BookStatsRepository bookStatsRepository;
    private final BookEvents bookEvents;

    public BookSearchListResult searchBooks(SearchBookCommand command) {

        // 검색이 유효하지 않더라도 검색 내역은 반영되도록
        bookEvents.bookSearched(BookSearchedEvent.of(command.userId(), command.query()));

        BookSearchListResult searchResponse =
                aladinProvider.searchItems(command.query(), command.queryType(),
                        command.pageable().getPageSize(), command.pageable().getPageNumber());

        if (searchResponse.items().isEmpty()) {
            return BookSearchListResult.empty();
        }

        List<String> bookIds = searchResponse.items().stream()
                .map(BookSummaryResult::bookId)
                .toList();

        Map<String, BookStats> statsMap = bookStatsRepository.findByIds(bookIds)
                .stream()
                .collect(Collectors.toMap(BookStats::getBookId, bookStats -> bookStats));

        log.debug("검색 결과 {}건, 통계 보유 {}건", searchResponse.items().size(), statsMap.size());

        List<BookSummaryResult> content = searchResponse.items().stream()
                .map(result -> Optional.ofNullable(statsMap.get(result.bookId()))
                        .map(result::withStats)
                        .orElse(result))
                .toList();

        return new BookSearchListResult(content, searchResponse.totalResults());
    }

    public BookDetailResult getBook(GetBookCommand command) {
        BookResult book = aladinProvider.getItem(command.bookId());

        BookDetailResult bookDetail = bookStatsRepository.findById(command.bookId())
                .map((stats) -> BookDetailResult.from(book).withStats(stats))
                .orElseGet(() -> BookDetailResult.from(book));

        bookEvents.bookSearched(
                BookSearchedEvent.ofDetail(command.userId(), command.bookId(), book.title(), book.categoryName(),
                        book.author()));

        return bookDetail;
    }

}
