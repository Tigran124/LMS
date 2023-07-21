package com.example.LMS.builder.book;

import com.example.LMS.dto.book.BookResponseDto;
import com.example.LMS.entity.Book;

import java.util.OptionalDouble;

public final class BookResponseBuilder {

    public static BookResponseDto buildBookResponseDto(Book book, OptionalDouble rate){
        BookResponseDto responseDto = new BookResponseDto();
        responseDto.setBookName(book.getBookName());
        responseDto.setId(book.getId());
        responseDto.setAuthorName(book.getAuthor().getAuthorName());
        responseDto.setAuthorId(book.getAuthor().getId());
        if (rate.isPresent()){
            responseDto.setRate(rate.getAsDouble());
        }else {
            responseDto.setRate(null);
        }
        return responseDto;
    }
}
