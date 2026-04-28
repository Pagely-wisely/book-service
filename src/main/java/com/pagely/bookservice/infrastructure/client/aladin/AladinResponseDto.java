package com.pagely.bookservice.infrastructure.client.aladin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pagely.bookservice.application.dto.result.BookResult;
import com.pagely.bookservice.infrastructure.client.aladin.exception.AladinErrorCode;
import com.pagely.bookservice.infrastructure.client.aladin.exception.detail.InvalidFormatAladinException;
import com.pagely.bookservice.infrastructure.client.aladin.exception.detail.NotFoundAladinItemException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AladinResponseDto(
        String title,
        int totalResults,
        List<AladinItemDto> item
) {
    private static final Logger log = LoggerFactory.getLogger(AladinResponseDto.class);

    public BookResult toBookResult() {
        if (item == null || item.isEmpty()) {
            throw new NotFoundAladinItemException();
        }

        AladinItemDto targetItem = item.getFirst();

        return BookResult.builder()
                .id(targetItem.isbn13())
                .title(targetItem.title())
                .author(targetItem.author())
                .publisher(targetItem.publisher())
                .thumbnailUrl(targetItem.cover())
                .description(targetItem.description())
                .publishedAt(parsePublishedAt(targetItem.pubDate()))
                .categoryId((long) targetItem.categoryId())
                .categoryName(targetItem.categoryName())
                .build();
    }

    private LocalDateTime parsePublishedAt(String pubDate) {
        if (pubDate == null || pubDate.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(pubDate).atStartOfDay();
        } catch (DateTimeParseException e) {
            log.error("input : {}", pubDate);
            throw new InvalidFormatAladinException(AladinErrorCode.ALADIN_INVALID_DATE_FORMAT);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record AladinItemDto(
            String title,
            String author,
            String pubDate,
            String description,
            String isbn13,
            String cover,
            int categoryId,
            String categoryName,
            String publisher,
            SeriesInfo seriesInfo,
            SubInfo subInfo
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record SeriesInfo(int seriesId, String seriesName, String seriesLink) {
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record SubInfo(String subTitle, String originalTitle, int itemPage) {
    }
}
