package com.pagely.bookservice.application.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record DeleteBookLikeCommand(
        @NotBlank
        @Size(max = 20)
        String bookId,

        @NotNull
        UUID userId
) {
}
