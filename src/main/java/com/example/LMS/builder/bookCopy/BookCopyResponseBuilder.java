package com.example.LMS.builder.bookCopy;

import com.example.LMS.dto.bookCopy.BookCopyResponseDto;
import com.example.LMS.entity.Availability;
import com.example.LMS.entity.BookCopy;

public final class BookCopyResponseBuilder {

    public static BookCopyResponseDto buildBookCopyResponseDto(BookCopy bookCopy){
        BookCopyResponseDto responseDto = new BookCopyResponseDto();
        responseDto.setId(bookCopy.getId());
        responseDto.setAvailability(bookCopy.getAvailability());
        responseDto.setBookId(bookCopy.getBook().getId());
        if (bookCopy.getAvailability().equals(Availability.TAKEN)){
            responseDto.setUserId(bookCopy.getUser().getId());
        }
        return responseDto;
    }
}
