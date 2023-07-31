package com.example.LMS.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCreateResponseDto {
    private Long bookId;
    private String bookName;
    private String authorName;
}
