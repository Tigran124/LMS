package com.example.LMS.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateResponseDto {
    private Long bookId;
    private Long userId;
}
