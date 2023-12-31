package com.example.LMS.builder.author;

import com.example.LMS.dto.author.AuthorCreateResponseDto;
import com.example.LMS.entity.Author;

public final class AuthorCreateResponseBuilder {

    public static AuthorCreateResponseDto createResponseDto(Author author){
        AuthorCreateResponseDto responseDto = new AuthorCreateResponseDto();
        responseDto.setId(author.getId());
        responseDto.setAuthorName(author.getAuthorName());
        return responseDto;
    }
}
