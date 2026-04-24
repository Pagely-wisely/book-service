package com.pagely.bookservice.global.config;

import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

// TODO: 임시로 사용할 auditing 설정. 추후 수정 요망
@Configuration
public class JpaAuditConfig {
    @Bean
    public AuditorAware<UUID> auditorAware() {
        return () -> Optional.ofNullable(UUID.randomUUID());
    }
}
