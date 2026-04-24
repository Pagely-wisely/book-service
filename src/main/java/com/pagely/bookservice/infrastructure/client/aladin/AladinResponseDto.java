package com.pagely.bookservice.infrastructure.client.aladin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pagely.bookservice.application.dto.result.BookResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AladinResponseDto {
    private String version;
    private String logo;
    private String title;
    private String link;
    private String pubDate;
    private int totalResults;
    private int startIndex;
    private int itemsPerPage;
    private String query;
    private int searchCategoryId;
    private String searchCategoryName;
    private List<AladinItemDto> item;

    public BookResult toItemResponse() {
        // TODO: 공통 예외 개발이후, null 체크 필요
        AladinItemDto item = this.getItem().getFirst();

        LocalDateTime publishedAt = null;
        if (item.getPubDate() != null && !item.getPubDate().isBlank()) {
            try {
                publishedAt = LocalDate.parse(item.getPubDate()).atStartOfDay();
            } catch (DateTimeParseException e) {
                log.error("알라딘 API 날짜 파싱 실패 ", e);
            }
        }
        return BookResult.builder()
                .id(item.getIsbn13())
                .title(item.getTitle())
                .author(item.getAuthor())
                .publisher(item.getPublisher())
                .thumbnailUrl(item.getCover())
                .description(item.getDescription())
                .publishedAt(
                        LocalDate.parse(item.getPubDate())
                                .atStartOfDay()
                )
                .categoryId((long) item.getCategoryId())
                .categoryName(item.getCategoryName())
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true) // 정의하지 않은 필드 무시
    public static class AladinItemDto {
        private String title;
        private String link;
        private String author;
        private String pubDate;
        private String description;
        private String isbn;
        private String isbn13;
        private Long itemId;
        private int priceSales;
        private int priceStandard;
        private String mallType;
        private String stockStatus;
        private int mileage;
        private String cover;
        private int categoryId;
        private String categoryName;
        private String publisher;
        private int salesPoint;
        private boolean adult;
        private boolean fixedPrice;
        private int customerReviewRank;

        private SeriesInfo seriesInfo;
        private SubInfo subInfo;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SeriesInfo {
            private int seriesId;
            private String seriesLink;
            private String seriesName;
        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SubInfo {
            private String subTitle;
            private String originalTitle;
            private int itemPage;
        }
    }
}
