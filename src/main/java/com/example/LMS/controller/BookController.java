package com.example.LMS.controller;

import com.example.LMS.dto.book.BookCreateRequestDto;
import com.example.LMS.dto.book.BookCreateResponseDto;
import com.example.LMS.dto.book.BookResponseDto;
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
    public BookCreateResponseDto createBook(@RequestBody BookCreateRequestDto requestDto) {
        return bookService.createBook(requestDto);
    }

    @GetMapping("/all")
    public List<BookResponseDto> getAllBooks(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        return bookService.getAllBooks(page, size);
    }

    @GetMapping("/{id}")
    public BookResponseDto getBookById(@PathVariable(value = "id") Long bookId) {
        return bookService.getBookById(bookId);
    }

    @GetMapping("/review/{id}")
    public List<ReviewResponseDto> getReviewListByBookId(
            @PathVariable(value = "id") Long bookId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        return bookService.getReviewListByBookId(bookId, page, size);
    }

    @GetMapping("/bookCopy/{id}")
    public List<BookCopyResponseDto> getBookCopyByBookId(
            @PathVariable(value = "id") Long bookId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        return bookService.getBookCopyByBookId(bookId, page, size);
    }

    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    public void deleteBook(@PathVariable(value = "id") Long bookId) {
        bookService.deleteBook(bookId);
    }
}
