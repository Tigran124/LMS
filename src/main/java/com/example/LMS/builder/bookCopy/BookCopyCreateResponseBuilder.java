package com.example.LMS.builder.bookCopy;

import com.example.LMS.dto.bookCopy.BookCopyCreateResponseDto;
import com.example.LMS.entity.BookCopy;

public final class BookCopyCreateResponseBuilder {

    public static BookCopyCreateResponseDto buildBookCopyResponseDto(BookCopy bookCopy){
        BookCopyCreateResponseDto responseDto = new BookCopyCreateResponseDto();
        responseDto.setBookId(bookCopy.getBook().getId());
        responseDto.setId(bookCopy.getId());
        responseDto.setAvailability(bookCopy.getAvailability());
        return responseDto;
    }
}
