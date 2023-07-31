package com.example.LMS.dto.bookCopy;

import com.example.LMS.entity.Availability;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCopyReturnResponseDto {

    private Long id;
    private Long bookId;
    private Availability availability;
}
