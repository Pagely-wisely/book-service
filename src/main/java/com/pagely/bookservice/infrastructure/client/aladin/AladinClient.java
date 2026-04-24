package com.pagely.bookservice.infrastructure.client.aladin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "aladin",
        url = "https://www.aladin.co.kr/ttb/api/ItemLookUp.aspx",
        configuration = AladinFeignConfig.class
)
public interface AladinClient {

    @GetMapping(value = "/getItem")
    AladinResponseDto getItem(
            @RequestParam("ItemId") String ItemId
    );
}
