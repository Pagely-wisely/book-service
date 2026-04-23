package com.pagely.bookservice.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Stats {
    private String bookId;
    private Integer reportCount;
    private Integer likeCount;

    // 감사 필드
    private LocalDateTime createdAt;
    private UUID createdBy;
    private LocalDateTime updatedAt;
    private UUID updatedBy;
    private LocalDateTime deletedAt;
    private UUID deletedBy;
}
