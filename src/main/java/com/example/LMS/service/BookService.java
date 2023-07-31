package com.example.LMS.service;

import com.example.LMS.builder.book.BookCreateResponseBuilder;
import com.example.LMS.builder.book.BookResponseBuilder;
import com.example.LMS.builder.bookCopy.BookCopyResponseBuilder;
import com.example.LMS.builder.review.ReviewResponseBuilder;
import com.example.LMS.dto.book.BookCreateRequestDto;
import com.example.LMS.dto.book.BookCreateResponseDto;
import com.example.LMS.dto.book.BookResponseDto;
import com.example.LMS.dto.book.BookUnitResponseDto;
import com.example.LMS.dto.bookCopy.BookCopyResponseDto;
import com.example.LMS.dto.review.ReviewResponseDto;
import com.example.LMS.entity.Author;
import com.example.LMS.entity.Book;
import com.example.LMS.entity.Review;
import com.example.LMS.exception.NoContentToDeleteException;
import com.example.LMS.exception.ResourceNotFoundException;
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
        Author author = getAuthor(requestDto.getAuthorId());
        Book book = new Book();
        book.setBookName(requestDto.getBookName());
        book.setAuthor(author);
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

    public BookUnitResponseDto getBookById(Long bookId){
        Book book = getBook(bookId);
        BookUnitResponseDto responseDto = new BookUnitResponseDto();
        responseDto.setBookName(book.getBookName());
        responseDto.setAuthorName(book.getAuthor().getAuthorName());
        responseDto.setAuthorId(book.getAuthor().getId());
        OptionalDouble rate = calculateRate(book);
        if (rate.isPresent()){
            responseDto.setRate(rate.getAsDouble());
        }else {
            responseDto.setRate(null);
        }
        responseDto.setReviewList(book
                .getReviewList()
                .stream()
                .map(ReviewResponseBuilder::buildReviewResponseDto)
                .collect(Collectors.toList()));
        return responseDto;
    }

    public List<ReviewResponseDto> getReviewListByBookId(Long bookId){
        Book book = getBook(bookId);
        return book
                .getReviewList()
                .stream()
                .map(ReviewResponseBuilder::buildReviewResponseDto)
                .collect(Collectors.toList());
    }

    public List<BookCopyResponseDto> getBookCopyByBookId(Long bookId){
        Book book = getBook(bookId);
        return book
                .getBookCopyList()
                .stream()
                .map(BookCopyResponseBuilder::buildBookCopyResponseDto)
                .collect(Collectors.toList());
    }

    public void deleteBook(Long bookId){
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()){
            throw new NoContentToDeleteException("Book not found to delete");
        }
        bookRepository.delete(optionalBook.get());
    }

    public OptionalDouble calculateRate(Book book){
        return book.getReviewList().stream()
                .mapToDouble(Review::getRate)
                .average();
    }

    private Author getAuthor(Long authorId){
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isEmpty()){
            throw new ResourceNotFoundException("Author not found");
        }
        return optionalAuthor.get();
    }

    private Book getBook(Long bookId){
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()){
            throw new ResourceNotFoundException("Book not found");
        }
        return optionalBook.get();
    }
}
