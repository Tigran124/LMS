package com.example.LMS.builder.review;

import com.example.LMS.dto.review.ReviewResponseDto;
import com.example.LMS.entity.Review;

public final class ReviewResponseBuilder {

    public static ReviewResponseDto buildReviewResponseDto(Review review){
        ReviewResponseDto responseDto = new ReviewResponseDto();
        responseDto.setRate(review.getRate());
        responseDto.setComment(review.getComment());
        responseDto.setUserId(review.getUser().getId());
        responseDto.setBookId(review.getBook().getId());
        return responseDto;
    }
}
