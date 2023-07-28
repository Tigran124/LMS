package com.example.LMS.service;

import com.example.LMS.builder.bookCopy.BookCopyResponseBuilder;
import com.example.LMS.builder.review.ReviewCreateResponseBuilder;
import com.example.LMS.builder.review.ReviewResponseBuilder;
import com.example.LMS.dto.bookCopy.BookCopyResponseDto;
import com.example.LMS.dto.review.ReviewCreateRequestDto;
import com.example.LMS.dto.review.ReviewCreateResponseDto;
import com.example.LMS.dto.review.ReviewResponseDto;
import com.example.LMS.entity.*;
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
        Optional<User> optionalUser = getOptionalUser();
        Optional<Book> optionalBook = bookRepository.findById(requestDto.getBookId());
        if (optionalUser.isEmpty() || optionalBook.isEmpty()){
            throw new RuntimeException();
        }
        User user = optionalUser.get();
        Book book = optionalBook.get();
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
        Optional<User> optionalUser = getOptionalUser();
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalUser.isEmpty() || optionalBook.isEmpty()){
            throw new RuntimeException();
        }
        Optional<Review> optionalReview = reviewRepository.findByUserAndBookId(
                optionalUser.get(),
                optionalBook.get());
        if (optionalReview.isEmpty()){
            throw new RuntimeException();
        }
        return ReviewResponseBuilder.buildReviewResponseDto(optionalReview.get());
    }

    public BookCopyResponseDto orderBookCopy(Long bookCopyId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> optionalUser = userRepository.findByUsername(currentPrincipalName);
        Optional<BookCopy> optionalBookCopy = bookCopyRepository.findById(bookCopyId);
        if (optionalUser.isEmpty() || optionalBookCopy.isEmpty()){
            throw new RuntimeException();
        }
        if (optionalBookCopy.get().getAvailability().equals(Availability.TAKEN)){
            throw new RuntimeException();
        }
        optionalBookCopy.get().setUser(optionalUser.get());
        optionalBookCopy.get().setAvailability(Availability.TAKEN);
        BookCopy savedBookCopy = bookCopyRepository.save(optionalBookCopy.get());
        return BookCopyResponseBuilder.buildBookCopyResponseDto(savedBookCopy);
    }

    private Optional<User> getOptionalUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findByUsername(currentPrincipalName);
    }
}
