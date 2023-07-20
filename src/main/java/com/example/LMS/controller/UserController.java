package com.example.LMS.controller;

import com.example.LMS.dto.bookCopy.BookCopyOrderRequestDto;
import com.example.LMS.dto.bookCopy.BookCopyResponseDto;
import com.example.LMS.dto.review.ReviewCreateRequestDto;
import com.example.LMS.dto.review.ReviewCreateResponseDto;
import com.example.LMS.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/review/create")
    public ReviewCreateResponseDto createReview(@RequestBody ReviewCreateRequestDto requestDto){
        return userService.createReview(requestDto);
    }

    @PutMapping("/bookCopy/put")
    public BookCopyResponseDto orderBookCopy(BookCopyOrderRequestDto requestDto){
        return userService.orderBookCopy(requestDto);
    }
}
