package com.pagely.bookservice.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "p_book_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(BookLike.BookLikeId.class)
@EntityListeners(AuditingEntityListener.class)
public class BookLike implements Serializable {

    @Id
    @Column(name = "book_id", length = 20)
    private String bookId;

    @Id
    @Column(name = "user_id", nullable = false, length = 36)
    private UUID userId;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private UUID createdBy;

    @Builder
    public BookLike(String bookId, UUID userId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    @Getter
    @EqualsAndHashCode
    @NoArgsConstructor
    public static class BookLikeId implements Serializable {
        private String bookId;
        private UUID userId;

        public BookLikeId(String bookId, UUID userId) {
            this.bookId = bookId;
            this.userId = userId;
        }
    }
}
