package com.example.LMS.controller;

import com.example.LMS.dto.book.BookCreateRequestDto;
import com.example.LMS.dto.book.BookCreateResponseDto;
import com.example.LMS.dto.book.BookResponseDto;
import com.example.LMS.service.BookService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/create")
    @Secured("ADMIN")
    public BookCreateResponseDto createBook(@RequestBody BookCreateRequestDto requestDto){
        return bookService.createBook(requestDto);
    }

    @GetMapping("/getAll")
    public List<BookResponseDto> getAllBooks(){
        return bookService.getAllBooks();
    }

}
