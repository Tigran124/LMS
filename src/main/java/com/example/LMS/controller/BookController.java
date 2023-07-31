package com.example.LMS.controller;

import com.example.LMS.dto.book.BookCreateRequestDto;
import com.example.LMS.dto.book.BookCreateResponseDto;
import com.example.LMS.dto.book.BookResponseDto;
import com.example.LMS.dto.book.BookUnitResponseDto;
import com.example.LMS.dto.bookCopy.BookCopyResponseDto;
import com.example.LMS.dto.review.ReviewResponseDto;
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

    @PostMapping
    @Secured("ADMIN")
    public BookCreateResponseDto createBook(@RequestBody BookCreateRequestDto requestDto){
        return bookService.createBook(requestDto);
    }

    @GetMapping("/all")
    public List<BookResponseDto> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookUnitResponseDto getBookById(@PathVariable(value = "id") Long bookId){
        return bookService.getBookById(bookId);
    }

    @GetMapping("/review/{id}")
    public List<ReviewResponseDto> getReviewListByBookId(@PathVariable(value = "id") Long bookId){
        return bookService.getReviewListByBookId(bookId);
    }

    @GetMapping("/bookCopy/{id}")
    public List<BookCopyResponseDto> getBookCopyByBookId(@PathVariable(value = "id") Long bookId){
        return bookService.getBookCopyByBookId(bookId);
    }

    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    public void deleteBook(@PathVariable(value = "id") Long bookId){
        bookService.deleteBook(bookId);
    }
}
