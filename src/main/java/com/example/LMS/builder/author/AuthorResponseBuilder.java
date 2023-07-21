package com.example.LMS.builder.author;

import com.example.LMS.dto.author.AuthorResponseDto;
import com.example.LMS.entity.Author;

public final class AuthorResponseBuilder {

    public static AuthorResponseDto buildAuthorResponseDto(Author author){
        AuthorResponseDto responseDto = new AuthorResponseDto();
        responseDto.setId(author.getId());
        responseDto.setAuthorName(author.getAuthorName());
        return responseDto;
    }
}
