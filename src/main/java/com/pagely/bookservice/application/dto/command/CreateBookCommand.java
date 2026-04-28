package com.pagely.bookservice.application.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateBookCommand(
        @NotBlank
        @Size(max = 20)
        String bookId
) {
}
