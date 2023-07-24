package com.example.LMS.service;

import com.example.LMS.builder.bookCopy.BookCopyResponseBuilder;
import com.example.LMS.builder.review.ReviewCreateResponseBuilder;
import com.example.LMS.dto.bookCopy.BookCopyResponseDto;
import com.example.LMS.dto.review.ReviewCreateRequestDto;
import com.example.LMS.dto.review.ReviewCreateResponseDto;
import com.example.LMS.entity.*;
import com.example.LMS.repository.BookCopyRepository;
import com.example.LMS.repository.BookRepository;
import com.example.LMS.repository.ReviewRepository;
import com.example.LMS.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.provider.HibernateUtils;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> optionalUser = userRepository.findByUsername(currentPrincipalName);
        Optional<Book> optionalBook = bookRepository.findById(requestDto.getBookId());
        if (optionalUser.isEmpty() || optionalBook.isEmpty()){
            throw new RuntimeException();
        }
        User user = optionalUser.get();
        Review review1 = reviewRepository.findByUserAndBookId(user, optionalBook.get());
        String comm = review1.getComment();
        try {
            Review review = new Review();
            review.setUser(user);
            review.setBook(optionalBook.get());
            review.setComment(requestDto.getComment());
            review.setRate(requestDto.getRate());
            Review savedReview = reviewRepository.save(review);
            return ReviewCreateResponseBuilder.buildReviewCreateResponseDto(savedReview);
        }catch (DataIntegrityViolationException e){
            Review review = reviewRepository.findByUserAndBookId(user, optionalBook.get());
            review.setRate(requestDto.getRate());
            review.setComment(requestDto.getComment());
            Review savedReview = reviewRepository.save(review);
            return ReviewCreateResponseBuilder.buildReviewCreateResponseDto(savedReview);
        }
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
}
