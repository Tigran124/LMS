package com.example.LMS.service;

import com.example.LMS.configuration.BookCopyFactory;
import com.example.LMS.dto.bookCopy.BookCopyCreateRequestDto;
import com.example.LMS.dto.bookCopy.BookCopyCreateResponseDto;
import com.example.LMS.entity.Book;
import com.example.LMS.repository.BookCopyRepository;
import com.example.LMS.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<BookCopyCreateResponseDto> createBookCopy(BookCopyCreateRequestDto requestDto){
        Optional<Book> optionalBook = bookRepository.findById(requestDto.getBookId());
        if (optionalBook.isEmpty()){
            throw new  RuntimeException();
        }
        return bookCopyFactory.createBookCopy(requestDto.getAmount(), optionalBook.get());
    }
}
