package com.example.LMS.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCreateRequestDto {
    private String bookName;
    private Long authorId;
}
