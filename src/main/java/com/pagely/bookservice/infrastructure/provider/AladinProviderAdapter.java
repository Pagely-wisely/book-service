package com.pagely.bookservice.infrastructure.provider;

import com.pagely.bookservice.application.dto.result.BookResult;
import com.pagely.bookservice.application.port.AladinProvider;
import com.pagely.bookservice.infrastructure.client.aladin.AladinClient;
import com.pagely.bookservice.infrastructure.client.aladin.AladinResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AladinProviderAdapter implements AladinProvider {
    private final AladinClient aladinClient;

    @Override
    public BookResult getItem(String bookId) {
        AladinResponseDto item = aladinClient.getItem(bookId);
        return item.toItemResponse();
    }
}
