package com.example.LMS.controller;

import com.example.LMS.dto.bookCopy.BookCopyCreateRequestDto;
import com.example.LMS.dto.bookCopy.BookCopyCreateResponseDto;
import com.example.LMS.service.BookCopyService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookCopy")
public class BookCopyController {

    private final BookCopyService bookCopyService;

    public BookCopyController(BookCopyService bookCopyService) {
        this.bookCopyService = bookCopyService;
    }

    @Secured("ADMIN")
    @PostMapping("/create")
    public List<BookCopyCreateResponseDto> createBookCopy(@RequestBody BookCopyCreateRequestDto requestDto){
        return bookCopyService.createBookCopy(requestDto);
    }
}
