package com.example.LMS.builder.book;

import com.example.LMS.dto.book.BookCreateResponseDto;
import com.example.LMS.entity.Book;

public final class BookCreateResponseBuilder {

    public static BookCreateResponseDto buildBookCreateResponseDto(Book book){
        BookCreateResponseDto responseDto = new BookCreateResponseDto();
        responseDto.setBookId(book.getId());
        responseDto.setBookName(book.getBookName());
        responseDto.setAuthorName(book.getAuthor().getAuthorName());
        return responseDto;
    }
}
