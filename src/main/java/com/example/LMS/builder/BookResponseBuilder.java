package com.example.LMS.builder;

import com.example.LMS.dto.book.BookResponseDto;
import com.example.LMS.entity.Book;

public final class BookResponseBuilder {

    public static BookResponseDto buildBookResponseDto(Book book){
        BookResponseDto responseDto = new BookResponseDto();
        responseDto.setBookName(book.getBookName());
        responseDto.setId(book.getId());
        responseDto.setAuthorName(book.getAuthor().getAuthorName());
        responseDto.setAuthorId(book.getAuthor().getId());
        responseDto.setRate(10.);
        return responseDto;
    }
}
