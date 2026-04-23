package com.pagely.bookservice.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@Entity
//@Table(name = "p_book)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {
    //    @Id
//    @Column(nullable = false, length = 20)
    private String id;

    //    @Column(nullable = false)
    private String title;

    //    @Column(nullable = false)
    private String author;

    //    @Column(nullable = false)
    private String publisher;

    //    @Column(name = "published_at", nullable = false)
    private LocalDateTime publishedAt;

    //    @Column(name = "thumbnail_url", nullable = false, length = 500)
    private String thumbnailUrl;

    //    @Column(length = 500)
    private String description;

    //    @Embedded
    private Category category;

    // 감사 필드
//    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    //    @Column(name = "created_by", nullable = false)
    private UUID createdBy;
    //    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    //    @Column(name = "updated_by")
    private UUID updatedBy;
    //    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    //    @Column(name = "deleted_by")
    private UUID deletedBy;

}
