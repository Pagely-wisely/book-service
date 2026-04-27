package com.pagely.bookservice.domain.service;

import com.pagely.bookservice.domain.model.BookLike;
import com.pagely.bookservice.domain.model.BookLike.BookLikeId;
import com.pagely.bookservice.domain.repository.BookLikeRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookLikeDeleteService {
    private final BookLikeRepository bookLikeRepository;

    // TODO: Role 추가 시, 관리자는 무조건 삭제할 수 있도록 수정
    public void deleteBookLike(BookLike bookLike, UUID requester) {
        if (!bookLike.getUserId().equals(requester)) {
            log.debug("좋아요 생성 유저: {} , 요청 유저: {}", bookLike.getUserId(), requester);
            // TODO: 도메인 예외로 수정해야 함
            throw new IllegalArgumentException();
        }
        bookLikeRepository.deleteById(new BookLikeId(bookLike.getBookId(), bookLike.getUserId()));
    }
}
