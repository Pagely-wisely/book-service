package com.pagely.bookservice.infrastructure.client.aladin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pagely.bookservice.application.dto.result.BookSearchListResult;
import com.pagely.bookservice.application.dto.result.BookSummaryResult;
import java.util.List;

public record AladinSearchResponseDto(
        int totalResults,
        int startIndex,
        int itemsPerPage,
        List<AladinSearchItemDto> item
) {
    public BookSearchListResult toBookSearchResponse() {
        if (item == null || item.isEmpty()) {
            return BookSearchListResult.empty();
        }
        List<BookSummaryResult> results = item.stream()
                .map(i -> BookSummaryResult.of(
                        i.resolveBookId(),
                        i.title(),
                        i.author(),
                        i.categoryName,
                        i.publisher(),
                        i.cover()
                ))
                .toList();
        return new BookSearchListResult(results, totalResults);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record AladinSearchItemDto(
            String title,
            String author,
            String categoryName,
            String publisher,
            String isbn,
            String isbn13,
            long itemId,
            String cover
    ) {
        public String resolveBookId() {
            if (isbn13 != null && !isbn13.isBlank()) {
                return isbn13;
            }
            if (isbn != null && !isbn.isBlank()) {
                return isbn;
            }
            return String.valueOf(itemId);
        }
    }
}
