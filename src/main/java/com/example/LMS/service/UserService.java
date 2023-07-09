package com.example.LMS.service;

import com.example.LMS.builder.ReviewCreateResponseBuilder;
import com.example.LMS.dto.review.ReviewCreateRequestDto;
import com.example.LMS.dto.review.ReviewCreateResponseDto;
import com.example.LMS.entity.Book;
import com.example.LMS.entity.Review;
import com.example.LMS.entity.User;
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

    public UserService(UserRepository userRepository, BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
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
            Review review = reviewRepository.updateById(
                    requestDto.getComment(),
                    requestDto.getRate(),
                    reviewId);
            user.addReview(requestDto.getBookId(), review);
            return ReviewCreateResponseBuilder.buildReviewCreateResponseDto(review);
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
}
