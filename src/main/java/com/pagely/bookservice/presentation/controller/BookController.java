package com.pagely.bookservice.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/books")
@RestController
public class BookController {

    @GetMapping
    String hello() {
        return "HelloWorld!";
    }

}
