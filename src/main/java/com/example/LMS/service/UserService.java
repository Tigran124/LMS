package com.example.LMS.service;

import com.example.LMS.builder.bookCopy.BookCopyResponseBuilder;
import com.example.LMS.builder.bookCopy.BookCopyReturnResponseBuilder;
import com.example.LMS.builder.review.ReviewCreateResponseBuilder;
import com.example.LMS.builder.review.ReviewResponseBuilder;
import com.example.LMS.dto.bookCopy.BookCopyResponseDto;
import com.example.LMS.dto.bookCopy.BookCopyReturnResponseDto;
import com.example.LMS.dto.review.ReviewCreateRequestDto;
import com.example.LMS.dto.review.ReviewCreateResponseDto;
import com.example.LMS.dto.review.ReviewResponseDto;
import com.example.LMS.entity.*;
import com.example.LMS.exception.NoContentToDeleteException;
import com.example.LMS.exception.ResourceNotFoundException;
import com.example.LMS.exception.ValidationException;
import com.example.LMS.repository.BookCopyRepository;
import com.example.LMS.repository.BookRepository;
import com.example.LMS.repository.ReviewRepository;
import com.example.LMS.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class  UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final BookCopyRepository bookCopyRepository;

    public UserService(UserRepository userRepository, BookRepository bookRepository, ReviewRepository reviewRepository, BookCopyRepository bookCopyRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    public ReviewCreateResponseDto createReview(ReviewCreateRequestDto requestDto){
        User user = getUser();
        Book book = getBook(requestDto.getBookId());
        Optional<Review> optionalReview = reviewRepository.findByUserAndBookId(user, book);
        if (optionalReview.isEmpty()){
            Review review = new Review();
            review.setUser(user);
            review.setBook(book);
            review.setComment(requestDto.getComment());
            review.setRate(requestDto.getRate());
            Review savedReview = reviewRepository.save(review);
            return ReviewCreateResponseBuilder.buildReviewCreateResponseDto(savedReview);
        }else {
            Review review = optionalReview.get();
            review.setRate(requestDto.getRate());
            review.setComment(requestDto.getComment());
            Review savedReview = reviewRepository.save(review);
            return ReviewCreateResponseBuilder.buildReviewCreateResponseDto(savedReview);
        }
    }

    public ReviewResponseDto getReviewByBookId(Long bookId){
        User user = getUser();
        Book book = getBook(bookId);
        Review review = getReview(user, book);
        return ReviewResponseBuilder.buildReviewResponseDto(review);
    }

    public BookCopyResponseDto orderBookCopy(Long bookCopyId){
        User user = getUser();
        BookCopy bookCopy = getBookCopy(bookCopyId);
        if (bookCopy.getAvailability().equals(Availability.TAKEN)){
            throw new ValidationException("BookCopy is already taken");
        }
        bookCopy.setUser(user);
        bookCopy.setAvailability(Availability.TAKEN);
        BookCopy savedBookCopy = bookCopyRepository.save(bookCopy);
        return BookCopyResponseBuilder.buildBookCopyResponseDto(savedBookCopy);
    }

    public BookCopyReturnResponseDto returnBookCopy(Long bookCopyId){
        User user = getUser();
        BookCopy bookCopy = getBookCopy(bookCopyId);
        if (bookCopy.getAvailability().equals(Availability.AVAILABLE)){
            throw new ResourceNotFoundException("Wrong bookCopy Id");
        }
        bookCopy.setAvailability(Availability.AVAILABLE);
        bookCopy.removeUser();
        return BookCopyReturnResponseBuilder
                .buildBookCopyReturnResponseDto(bookCopyRepository.save(bookCopy));
    }

    public void deleteUser(){
        User user = getUser();
        if (!user.getOrderedBook().isEmpty()){
            throw new ValidationException("You have to return books to delete account");
        }
        userRepository.delete(user);
    }

    public void deleteReviewByBookId(Long bookId){
        User user = getUser();
        Book book = getBook(bookId);
        Optional<Review> optionalReview = reviewRepository.findByUserAndBookId(user, book);
        if (optionalReview.isEmpty()){
            throw new NoContentToDeleteException("Review not found to delete");
        }
        reviewRepository.delete(optionalReview.get());
    }

    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> optionalUser = userRepository.findByUsername(currentPrincipalName);
        if (optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User not found");
        }
        return optionalUser.get();
    }

    private Book getBook(Long bookId){
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()){
            throw new ResourceNotFoundException("Book not found");
        }
        return optionalBook.get();
    }

    private Review getReview(User user, Book book){
        Optional<Review> optionalReview = reviewRepository.findByUserAndBookId(user, book);
        if (optionalReview.isEmpty()){
            throw new ResourceNotFoundException("Review not found");
        }
        return optionalReview.get();
    }

    private BookCopy getBookCopy(Long bookCopyId){
        Optional<BookCopy> optionalBookCopy = bookCopyRepository.findById(bookCopyId);
        if (optionalBookCopy.isEmpty()){
            throw new ResourceNotFoundException("BookCopy not found");
        }
        return optionalBookCopy.get();
    }
}
