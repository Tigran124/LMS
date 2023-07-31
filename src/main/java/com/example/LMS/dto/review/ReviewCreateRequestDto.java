package com.example.LMS.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequestDto {
    private Long bookId;
    private Double rate;
    private String comment;
}
