package com.pagely.bookservice.infrastructure.client.aladin;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AladinFeignConfig {
    private final String output = "js";
    private final String itemIdType = "ISBN13";
    private final String version = "20131101";
    @Value("${api.aladin.ttbkey}")
    private String ttbkey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.query("ttbkey", ttbkey);
            requestTemplate.query("output", output);
            requestTemplate.query("itemIdType", itemIdType);
            requestTemplate.query("Version", version);
        };
    }

}
