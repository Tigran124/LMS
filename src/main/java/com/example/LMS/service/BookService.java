package com.example.LMS.service;

import com.example.LMS.builder.BookCreateResponseBuilder;
import com.example.LMS.builder.BookResponseBuilder;
import com.example.LMS.builder.ReviewResponseBuilder;
import com.example.LMS.dto.book.BookCreateRequestDto;
import com.example.LMS.dto.book.BookCreateResponseDto;
import com.example.LMS.dto.book.BookResponseDto;
import com.example.LMS.dto.review.ReviewResponseDto;
import com.example.LMS.entity.Author;
import com.example.LMS.entity.Book;
import com.example.LMS.entity.Review;
import com.example.LMS.repository.AuthorRepository;
import com.example.LMS.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
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
                .map((Book book) -> BookResponseBuilder.buildBookResponseDto(book,
                        calculateRate(book)))
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDto> getReviewByBookId(Long bookId){
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()){
            throw new  RuntimeException();
        }
        return optionalBook.get()
                .getReviewList()
                .stream()
                .map(ReviewResponseBuilder::buildReviewResponseDto)
                .collect(Collectors.toList());
    }

    public OptionalDouble calculateRate(Book book){
        return book.getReviewList().stream()
                .mapToDouble(Review::getRate)
                .average();
    }
}
