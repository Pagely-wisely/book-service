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
    private final BookService bookService;

    @Async
    public void generateBooksFromFile() {
        try {
            ClassPathResource resource = new ClassPathResource("isbn13_list.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            String isbn;
            while ((isbn = reader.readLine()) != null) {
                if (isbn.isBlank()) {
                    continue;
                }
                try {
                    bookService.getByInternalBook(CreateBookCommand.builder()
                            .id(isbn.trim())
                            .build());
                    Thread.sleep(100);
                } catch (Exception e) {
                    log.error("도서 저장 실패: {} - {}", isbn, e.getMessage());
                }
            }
        } catch (IOException e) {
            log.error("파일 읽기 실패", e);
        }
    }
}
