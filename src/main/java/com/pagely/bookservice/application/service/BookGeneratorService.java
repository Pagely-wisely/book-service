package com.pagely.bookservice.application.service;

import com.pagely.bookservice.application.dto.command.CreateBookCommand;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookGeneratorService {
    private final BookGetOrCreateService bookService;

    @Async("bookGeneratorExecutor")
    public void generateBooksFromFile() {
        try {
            ClassPathResource resource = new ClassPathResource("isbn13_list.txt");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                String isbn;
                while ((isbn = reader.readLine()) != null) {
                    if (isbn.isBlank()) {
                        continue;
                    }
                    try {
                        bookService.getOrCreateBook(new CreateBookCommand(isbn));
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.warn("도서 생성 작업이 인터럽트되어 중단됩니다. ISBN: {}", isbn);
                        break;
                    } catch (Exception e) {
                        log.error("도서 저장 실패: {} - {}", isbn, e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            log.error("파일 읽기 실패", e);
        }
    }
}
