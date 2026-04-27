package com.pagely.bookservice.domain.model;

import com.pagely.bookservice.domain.service.BookLikeDeleteService;
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

/**
 * <h1>좋아요</h1>
 * <p>
 * 도서 좋아요는 도서의 id를 참조하지만 FK 제약 조건이 걸려있지 않는 설계
 * <p>
 * 이유
 * <ul>
 *     <li>도서 삭제는 현재 비즈니스 로직 상 존재하지않음</li>
 *     <li>도서가 삭제 된다면 인적 오류로, 언제든지 외부 api를 통해 도서 정보 복구 가능</li>
 *     <li>ISBN을 key 값으로 사용하기에 어떤 경로를 통해서 도서 정보를 받아오더라도 같은 도서임이 보증됨</li>
 * </ul>
 */
@Entity
@Table(name = "p_book_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(BookLike.BookLikeId.class)
@EntityListeners(AuditingEntityListener.class)
public class BookLike {

    @Id
    @Column(name = "book_id", nullable = false, length = 20)
    private String bookId;

    @Id
    @Column(name = "user_id", nullable = false)
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

    public void hardDelete(BookLikeId requester, BookLikeDeleteService bookLikeDeleteService) {
        bookLikeDeleteService.deleteBookLike(this, requester);
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
