package com.example.LMS.controller;

import com.example.LMS.dto.bookCopy.BookCopyResponseDto;
import com.example.LMS.dto.bookCopy.BookCopyReturnResponseDto;
import com.example.LMS.dto.review.ReviewCreateRequestDto;
import com.example.LMS.dto.review.ReviewCreateResponseDto;
import com.example.LMS.dto.review.ReviewResponseDto;
import com.example.LMS.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/review")
    public ReviewCreateResponseDto createReview(@RequestBody ReviewCreateRequestDto requestDto){
        return userService.createReview(requestDto);
    }

    @GetMapping("/review/{id}")
    public ReviewResponseDto getReviewByBookId(@PathVariable(value = "id") Long bookId){
        return userService.getReviewByBookId(bookId);
    }

    @PutMapping("/bookCopy/order/{id}")
    public BookCopyResponseDto orderBookCopy(@PathVariable(value = "id") Long bookCopyId){
        return userService.orderBookCopy(bookCopyId);
    }

    @PutMapping("/bookCopy/return/{id}")
    public BookCopyReturnResponseDto returnBookCopy(@PathVariable(value = "id") Long bookCopyId){
        return userService.returnBookCopy(bookCopyId);
    }

    @DeleteMapping
    public void deleteUser(){
        userService.deleteUser();
    }

    @DeleteMapping("/review/{id}")
    public void deleteReviewByBookId(@PathVariable(value = "id") Long bookId){
        userService.deleteReviewByBookId(bookId);
    }
}
