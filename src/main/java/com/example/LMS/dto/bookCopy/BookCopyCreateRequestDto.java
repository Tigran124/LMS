package com.example.LMS.dto.bookCopy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCopyCreateRequestDto {

    private Long bookId;
    private Long amount;
}
