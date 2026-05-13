package com.pagely.bookservice.infrastructure.provider;

import com.pagely.bookservice.application.dto.result.BookResult;
import com.pagely.bookservice.application.dto.result.BookSearchListResult;
import com.pagely.bookservice.application.port.out.AladinProvider;
import com.pagely.bookservice.infrastructure.client.aladin.AladinClient;
import com.pagely.bookservice.infrastructure.client.aladin.AladinResponseDto;
import com.pagely.bookservice.infrastructure.client.aladin.AladinSearchResponseDto;
import com.pagely.bookservice.infrastructure.client.aladin.exception.detail.NotFoundAladinItemException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AladinProviderAdapter implements AladinProvider {
    private static final String DEFAULT_ITEM_ID_TYPE = "ISBN13";
    private static final String DEFAULT_SEARCH_TARGET = "Book";
    private final AladinClient aladinClient;


    @Override
    public BookResult getItem(String bookId) {
        log.debug("알라딘 API 도서정보 가져오기 bookId:{}", bookId);
        AladinResponseDto item = aladinClient.getItem(bookId, DEFAULT_ITEM_ID_TYPE);
        if (Objects.isNull(item)
                || Objects.isNull(item.item())) {
            throw new NotFoundAladinItemException();
        }
        return item.toBookResult();
    }

    @Override
    public BookSearchListResult searchItems(String query, String queryType, int size, int page) {
        AladinSearchResponseDto response =
                aladinClient.searchItems(query, queryType, size, page + 1,
                        DEFAULT_SEARCH_TARGET);

        if (Objects.isNull(response)) {
            return BookSearchListResult.empty();
        }

        return response.toBookSearchResponse();
    }

}
