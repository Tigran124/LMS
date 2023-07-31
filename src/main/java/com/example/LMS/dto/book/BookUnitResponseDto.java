package com.example.LMS.dto.book;

import com.example.LMS.dto.review.ReviewResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookUnitResponseDto {

    private String bookName;
    private Long authorId;
    private String authorName;
    private Double rate;
    private List<ReviewResponseDto> reviewList;
}
