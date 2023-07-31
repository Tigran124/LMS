package com.example.LMS.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponseDto {
    private Long id;
    private String bookName;
    private String authorName;
    private Long authorId;
    private Double rate;
}
