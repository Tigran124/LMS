package com.example.LMS.builder.review;

import com.example.LMS.dto.review.ReviewCreateResponseDto;
import com.example.LMS.entity.Review;

public final class ReviewCreateResponseBuilder {

    public static ReviewCreateResponseDto buildReviewCreateResponseDto(Review review){
        ReviewCreateResponseDto responseDto = new ReviewCreateResponseDto();
        responseDto.setId(review.getId());
        responseDto.setBookId(review.getBook().getId());
        responseDto.setUserId(review.getUser().getId());
        return responseDto;
    }
}
