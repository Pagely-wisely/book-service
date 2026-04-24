-- p_book
CREATE TABLE IF NOT EXISTS p_book
(
    id            VARCHAR(20)  NOT NULL,
    title         VARCHAR(255) NOT NULL,
    authors       VARCHAR(255) NOT NULL,
    publisher     VARCHAR(255) NOT NULL,
    thumbnail_url VARCHAR(500) NOT NULL,
    description   VARCHAR(500),
    published_at  TIMESTAMP    NOT NULL,
    category_id   BIGINT,
    category_name VARCHAR(50),
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by    UUID         NOT NULL,
    updated_at    TIMESTAMP,
    updated_by    UUID,
    deleted_at    TIMESTAMP,
    deleted_by    UUID,

    CONSTRAINT pk_book PRIMARY KEY (id)
    );


-- p_book_like
CREATE TABLE IF NOT EXISTS p_book_like
(
    book_id    VARCHAR(20) NOT NULL,
    user_id    UUID        NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT NOW(),
    created_by UUID        NOT NULL,

    CONSTRAINT pk_book_like PRIMARY KEY (book_id, user_id)
    );


-- p_book_stats
CREATE TABLE IF NOT EXISTS p_book_stats
(
    book_id      VARCHAR(20) NOT NULL,
    report_count INT         NOT NULL DEFAULT 0,
    like_count   INT         NOT NULL DEFAULT 0,
    created_at   TIMESTAMP   NOT NULL DEFAULT NOW(),
    created_by   UUID        NOT NULL,
    updated_at   TIMESTAMP,
    updated_by   UUID,
    deleted_at    TIMESTAMP,
    deleted_by    UUID,

    CONSTRAINT pk_book_stats    PRIMARY KEY (book_id),
    CONSTRAINT chk_report_count CHECK (report_count >= 0),
    CONSTRAINT chk_like_count   CHECK (like_count >= 0)
    );
