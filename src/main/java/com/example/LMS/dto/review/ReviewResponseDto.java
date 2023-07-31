package com.example.LMS.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {

    private Double rate;
    private String comment;
    private Long userId;
    private Long bookId;
}
