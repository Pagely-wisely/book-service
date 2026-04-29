package com.pagely.bookservice.application.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record GetBookCommand(
        @NotBlank
        String bookId,

        @NotNull
        UUID userId
) {
}
