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
        // TODO: 공통모듈 개발 완료시, 공통 예외 상속받아 null 예외처리
        return item.toItemResponse();
    }
}
