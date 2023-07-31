package com.example.LMS.builder.bookCopy;

import com.example.LMS.dto.bookCopy.BookCopyReturnResponseDto;
import com.example.LMS.entity.BookCopy;

public final class BookCopyReturnResponseBuilder {

    public static BookCopyReturnResponseDto buildBookCopyReturnResponseDto(BookCopy bookCopy){
        BookCopyReturnResponseDto responseDto = new BookCopyReturnResponseDto();
        responseDto.setId(bookCopy.getId());
        responseDto.setBookId(bookCopy.getBook().getId());
        responseDto.setAvailability(bookCopy.getAvailability());
        return responseDto;
    }
}
