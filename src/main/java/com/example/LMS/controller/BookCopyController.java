package com.example.LMS.controller;

import com.example.LMS.dto.bookCopy.BookCopyCreateRequestDto;
import com.example.LMS.service.BookCopyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/bookCopy")
public class BookCopyController {

    private final BookCopyService bookCopyService;

    public BookCopyController(BookCopyService bookCopyService) {
        this.bookCopyService = bookCopyService;
    }

    @Secured("ADMIN")
    @PostMapping
    public ResponseEntity<HttpStatus> createBookCopy(@RequestBody BookCopyCreateRequestDto requestDto) {
        return bookCopyService.createBookCopy(requestDto);
    }
}
