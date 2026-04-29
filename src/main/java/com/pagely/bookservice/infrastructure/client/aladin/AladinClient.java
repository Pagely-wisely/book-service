package com.pagely.bookservice.infrastructure.client.aladin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// TODO: retry, CircuitBreaker 설정 추가 해야 됨
@FeignClient(
        name = "aladin",
        url = "https://www.aladin.co.kr/ttb/api",
        configuration = AladinFeignConfig.class
)
public interface AladinClient {

    @GetMapping(value = "/ItemLookUp.aspx")
    AladinResponseDto getItem(
            @RequestParam("ItemId")
            String ItemId,
            @RequestParam(value = "itemIdType", defaultValue = "ISBN13")
            String itemIdType
    );

    @GetMapping("/ItemSearch.aspx")
    AladinSearchResponseDto searchItems(
            @RequestParam("Query") String query,
            @RequestParam(value = "QueryType", defaultValue = "Title") String queryType,
            @RequestParam(value = "MaxResults", defaultValue = "10") int maxResults,
            @RequestParam(value = "start", defaultValue = "1") int start,
            @RequestParam(value = "SearchTarget", defaultValue = "Book") String searchTarget
    );

}
