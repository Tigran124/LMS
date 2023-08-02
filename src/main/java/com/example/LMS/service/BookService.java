package com.example.LMS.service;

import com.example.LMS.builder.book.BookCreateResponseBuilder;
import com.example.LMS.builder.book.BookResponseBuilder;
import com.example.LMS.builder.bookCopy.BookCopyResponseBuilder;
import com.example.LMS.builder.review.ReviewResponseBuilder;
import com.example.LMS.dto.book.BookCreateRequestDto;
import com.example.LMS.dto.book.BookCreateResponseDto;
import com.example.LMS.dto.book.BookResponseDto;
import com.example.LMS.dto.bookCopy.BookCopyResponseDto;
import com.example.LMS.dto.review.ReviewResponseDto;
import com.example.LMS.entity.Author;
import com.example.LMS.entity.Book;
import com.example.LMS.entity.BookCopy;
import com.example.LMS.entity.Review;
import com.example.LMS.exception.NoContentToDeleteException;
import com.example.LMS.exception.ResourceNotFoundException;
import com.example.LMS.repository.AuthorRepository;
import com.example.LMS.repository.BookCopyRepository;
import com.example.LMS.repository.BookRepository;
import com.example.LMS.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final ReviewRepository reviewRepository;
    private final BookCopyRepository bookCopyRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, ReviewRepository reviewRepository, BookCopyRepository bookCopyRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.reviewRepository = reviewRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    public BookCreateResponseDto createBook(BookCreateRequestDto requestDto) {
        Author author = getAuthor(requestDto.getAuthorId());
        Book book = new Book();
        book.setBookName(requestDto.getBookName());
        book.setAuthor(author);
        book.setReviewList(new ArrayList<>());
        book.setBookCopyList(new ArrayList<>());
        Book savedBook = bookRepository.save(book);
        return BookCreateResponseBuilder.buildBookCreateResponseDto(savedBook);
    }

    public List<BookResponseDto> getAllBooks(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findAll(pageable);
        if (bookPage.hasContent()) {
            return bookPage.getContent().stream()
                    .map(book ->
                            BookResponseBuilder.buildBookResponseDto(book, calculateRate(book)))
                    .collect(Collectors.toList());
        } else return new ArrayList<>();
    }

    public BookResponseDto getBookById(Long bookId) {
        Book book = getBook(bookId);
        return BookResponseBuilder.buildBookResponseDto(book, calculateRate(book));
    }

    public List<ReviewResponseDto> getReviewListByBookId(Long bookId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviewPage = reviewRepository.findByBook_Id(bookId, pageable);
        if (reviewPage.hasContent()) {
            return reviewPage.getContent().stream()
                    .map(ReviewResponseBuilder::buildReviewResponseDto)
                    .toList();
        } else return new ArrayList<>();
    }

    public List<BookCopyResponseDto> getBookCopyByBookId(Long bookId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookCopy> bookCopyPage = bookCopyRepository.findByBook_Id(bookId, pageable);
        if (bookCopyPage.hasContent()) {
            return bookCopyPage.getContent().stream()
                    .map(BookCopyResponseBuilder::buildBookCopyResponseDto)
                    .toList();
        } else return new ArrayList<>();
    }

    public void deleteBook(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new NoContentToDeleteException("Book not found to delete");
        }
        bookRepository.delete(optionalBook.get());
    }

    public OptionalDouble calculateRate(Book book) {
        return book.getReviewList().stream()
                .mapToDouble(Review::getRate)
                .average();
    }

    private Author getAuthor(Long authorId) {
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isEmpty()) {
            throw new ResourceNotFoundException("Author not found");
        }
        return optionalAuthor.get();
    }

    private Book getBook(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new ResourceNotFoundException("Book not found");
        }
        return optionalBook.get();
    }
}
