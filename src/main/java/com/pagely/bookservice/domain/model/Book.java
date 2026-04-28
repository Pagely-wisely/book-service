package com.pagely.bookservice.domain.model;

import com.pagely.bookservice.domain.event.Events;
import com.pagely.bookservice.domain.event.payload.BookCreatedEvent;
import com.pagely.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.util.StringUtils;

@Getter
@Entity
@Table(name = "p_book")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseEntity {
    private static final String DEFAULT_THUMBNAIL_URL = "http://default_img";
    private static final Long DEFAULT_CATEGORY_ID = 9999999L;
    private static final String DEFAULT_CATEGORY_NAME = "카테고리 미정";

    @Id
    @Column(nullable = false, length = 20)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String authors;

    @Column(nullable = false)
    private String publisher;

    @Column(name = "published_at", nullable = false)
    private LocalDateTime publishedAt;

    @Column(name = "thumbnail_url", nullable = false, length = 500)
    private String thumbnailUrl;

    @Column(length = 500)
    private String description;

    @Embedded
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "book_id",
            insertable = false, updatable = false)
    private BookStats stats;

    @Builder(access = AccessLevel.PRIVATE)
    private Book(String id, String title, String authors, String publisher,
                 String thumbnailUrl, String description, LocalDateTime publishedAt,
                 Category category) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.publishedAt = publishedAt;
        this.category = category;
    }

    /*
     * 도서 생성 팩토리 메서드
     * 도서가 생성 되는 기준은 internal API 로 도서 정보 요청 시
     * DB에 해당 도서에 대한 내용이 없으면, 외부 API로 도서 정보를 요청하고 저장합니다.
     */
    public static Book create(String id, String title, String authors, String publisher,
                              String thumbnailUrl, String description, LocalDateTime publishedAt,
                              Long categoryId, String categoryName) {
        Book book = Book.builder()
                .id(id)
                .title(title)
                .authors(authors)
                .publisher(publisher)
                .thumbnailUrl(resolveThumbnailUrl(thumbnailUrl))
                .description(description)
                .publishedAt(publishedAt)
                .category(resolveCategory(categoryId, categoryName))
                .build();
        Events.trigger(BookCreatedEvent.of(book));
        return book;
    }

    private static String resolveThumbnailUrl(String thumbnailUrl) {
        return StringUtils.hasText(thumbnailUrl) ? thumbnailUrl : DEFAULT_THUMBNAIL_URL;
    }

    private static Category resolveCategory(Long categoryId, String categoryName) {
        Long resolvedId = Objects.isNull(categoryId) ? DEFAULT_CATEGORY_ID : categoryId;
        String resolvedName = StringUtils.hasText(categoryName) ? categoryName : DEFAULT_CATEGORY_NAME;
        return Category.of(resolvedId, resolvedName);
    }
}
