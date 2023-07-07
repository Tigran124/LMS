package com.example.LMS.controller;

import com.example.LMS.dto.BookCreateRequestDto;
import com.example.LMS.dto.BookCreateResponseDto;
import com.example.LMS.dto.BookResponseDto;
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
    public BookCreateResponseDto createBook(@RequestBody BookCreateRequestDto bookCreateRequestDto){
        return bookService.createBook(bookCreateRequestDto);
    }

    @GetMapping("/getAll")
    public List<BookResponseDto> getAllBooks(){
        return bookService.getAllBooks();
    }

}
