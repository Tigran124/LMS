package com.example.LMS.service;

import com.example.LMS.builder.BookCreateResponseBuilder;
import com.example.LMS.builder.BookResponseBuilder;
import com.example.LMS.dto.BookCreateRequestDto;
import com.example.LMS.dto.BookCreateResponseDto;
import com.example.LMS.dto.BookResponseDto;
import com.example.LMS.entity.Author;
import com.example.LMS.entity.Book;
import com.example.LMS.repository.AuthorRepository;
import com.example.LMS.repository.BookRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public BookCreateResponseDto createBook(BookCreateRequestDto requestDto){
        Optional<Author> author = authorRepository.findById(requestDto.getAuthorId());
        if (author.isEmpty()){
            throw new RuntimeException();
        }
        Book book = new Book();
        book.setBookName(requestDto.getBookName());
        book.setAuthor(author.get());
        book.setReviewList(new ArrayList<>());
        book.setBookCopyList(new ArrayList<>());
        Book savedBook = bookRepository.save(book);
        return BookCreateResponseBuilder.buildBookCreateResponseDto(savedBook);
    }

    public List<BookResponseDto> getAllBooks(){
        return bookRepository.findAll()
                .stream()
                .map(BookResponseBuilder::buildBookResponseDto)
                .collect(Collectors.toList());
    }
}
