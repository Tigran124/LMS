package com.example.LMS.dto.author;

import com.example.LMS.dto.book.BookResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthorUnitResponseDto {

    private Long id;
    private String authorName;
    private List<BookResponseDto> bookResponseDtoList;
}
