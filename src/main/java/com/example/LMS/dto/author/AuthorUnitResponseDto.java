package com.example.LMS.dto.author;

import com.example.LMS.dto.book.BookResponseDto;

import java.util.List;

public class AuthorUnitResponseDto {

    private Long id;
    private String authorName;
    private List<BookResponseDto> bookResponseDtoList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<BookResponseDto> getBookResponseDtoList() {
        return bookResponseDtoList;
    }

    public void setBookResponseDtoList(List<BookResponseDto> bookResponseDtoList) {
        this.bookResponseDtoList = bookResponseDtoList;
    }
}
