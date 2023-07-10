package com.example.LMS.dto.book;

import com.example.LMS.dto.review.ReviewResponseDto;

import java.util.List;

public class BookUnitResponseDto {

    private String bookName;
    private String authorName;
    private Double rate;
    private List<ReviewResponseDto> reviewList;

}
