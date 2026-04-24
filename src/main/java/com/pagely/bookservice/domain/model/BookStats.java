package com.pagely.bookservice.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Table(name = "p_book_stats")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookStats extends BaseEntity {
    @Id
    @Column(name = "book_id", nullable = false, length = 20)
    private String bookId;

    @Column(name = "report_count", nullable = false)
    private int reportCount = 0;

    @Column(name = "like_count", nullable = false)
    private int likeCount = 0;

    @Builder
    public BookStats(String bookId) {
        this.bookId = bookId;
        this.reportCount = 0;
        this.likeCount = 0;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void increaseReportCount() {
        this.reportCount++;
    }

    public void decreaseReportCount() {
        if (this.reportCount > 0) {
            this.reportCount--;
        }
    }
}
