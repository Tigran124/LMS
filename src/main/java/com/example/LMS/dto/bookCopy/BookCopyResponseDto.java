package com.example.LMS.dto.bookCopy;

import com.example.LMS.entity.Availability;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCopyResponseDto {

    private Long id;
    private Availability availability;
    private Long bookId;
    private Long userId;
}
