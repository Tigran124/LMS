package com.example.LMS.service;

import com.example.LMS.configuration.BookCopyFactory;
import com.example.LMS.dto.bookCopy.BookCopyCreateRequestDto;
import com.example.LMS.entity.Book;
import com.example.LMS.exception.ResourceNotFoundException;
import com.example.LMS.repository.BookCopyRepository;
import com.example.LMS.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;
    private final BookCopyFactory bookCopyFactory;

    public BookCopyService(BookCopyRepository bookCopyRepository, BookRepository bookRepository, BookCopyFactory bookCopyFactory) {
        this.bookCopyRepository = bookCopyRepository;
        this.bookRepository = bookRepository;
        this.bookCopyFactory = bookCopyFactory;
    }

    public ResponseEntity<HttpStatus> createBookCopy(BookCopyCreateRequestDto requestDto) {
        Book book = getBook(requestDto.getBookId());
        bookCopyFactory.createBookCopy(requestDto.getAmount(), book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Book getBook(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new ResourceNotFoundException("Book not found");
        }
        return optionalBook.get();
    }
}
