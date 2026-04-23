package com.pagely.bookservice.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Like {
    private String bookId;
    private String userId;

    // 감사 필드
    private LocalDateTime createdAt;
    private UUID createdBy;
    private LocalDateTime updatedAt;
    private UUID updatedBy;
    private LocalDateTime deletedAt;
    private UUID deletedBy;
}
