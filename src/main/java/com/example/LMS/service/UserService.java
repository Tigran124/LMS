package com.example.LMS.service;

import com.example.LMS.builder.BookCopyResponseBuilder;
import com.example.LMS.builder.ReviewCreateResponseBuilder;
import com.example.LMS.dto.bookCopy.BookCopyOrderRequestDto;
import com.example.LMS.dto.bookCopy.BookCopyResponseDto;
import com.example.LMS.dto.review.ReviewCreateRequestDto;
import com.example.LMS.dto.review.ReviewCreateResponseDto;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> optionalUser = userRepository.findByUsername(currentPrincipalName);
        Optional<Book> optionalBook = bookRepository.findById(requestDto.getBookId());
        if (optionalUser.isEmpty() || optionalBook.isEmpty()){
            throw new RuntimeException();
        }
        User user = optionalUser.get();
        if (user.getReviewMap().containsKey(requestDto.getBookId())){
            Long reviewId = user.getReviewMap().
                    get(requestDto.getBookId()).getId();
            reviewRepository.updateById(
                    requestDto.getComment(),
                    requestDto.getRate(),
                    reviewId);
            Review savedReview = reviewRepository.getReferenceById(reviewId);
            user.addReview(requestDto.getBookId(), savedReview);
            return ReviewCreateResponseBuilder.buildReviewCreateResponseDto(savedReview);
        }else {
            Review review = new Review();
            review.setUser(user);
            review.setBook(optionalBook.get());
            review.setComment(requestDto.getComment());
            review.setRate(requestDto.getRate());
            Review savedReview = reviewRepository.save(review);
            user.addReview(optionalBook.get().getId(), savedReview);
            return ReviewCreateResponseBuilder.buildReviewCreateResponseDto(savedReview);
        }
    }

    public BookCopyResponseDto orderBookCopy(BookCopyOrderRequestDto requestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> optionalUser = userRepository.findByUsername(currentPrincipalName);
        Optional<Book> optionalBook = bookRepository.findById(requestDto.getBookId());
        Optional<BookCopy> optionalBookCopy = bookCopyRepository.findById(requestDto.getBookCopyId());
        if (optionalUser.isEmpty() || optionalBook.isEmpty() || optionalBookCopy.isEmpty()){
            throw new RuntimeException();
        }
        if (optionalBookCopy.get().getAvailability().equals(Availability.TAKEN)){
            throw new RuntimeException();
        }
        bookCopyRepository.updateById(optionalUser.get(), requestDto.getBookCopyId());
        optionalBookCopy.get().setUser(optionalUser.get());
        optionalBookCopy.get().setAvailability(Availability.TAKEN);
        BookCopy savedBookCopy = bookCopyRepository.getReferenceById(requestDto.getBookCopyId());
        return BookCopyResponseBuilder.buildBookCopyResponseDto(savedBookCopy);
    }
}
