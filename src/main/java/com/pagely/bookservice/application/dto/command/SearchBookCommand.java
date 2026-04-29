package com.pagely.bookservice.application.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public record SearchBookCommand(
        @NotNull
        UUID userId,

        @NotBlank
        String query,

        @NotBlank
        String queryType,

        @NotNull
        Pageable pageable
) {
}
