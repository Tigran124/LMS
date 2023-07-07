package com.example.LMS.service;

import com.example.LMS.builder.AuthorCreateResponseBuilder;
import com.example.LMS.dto.AuthorCreateRequestDto;
import com.example.LMS.dto.AuthorCreateResponseDto;
import com.example.LMS.entity.Author;
import com.example.LMS.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorCreateResponseDto createAuthor(AuthorCreateRequestDto requestDto){
        Author author = new Author();
        author.setAuthorName(requestDto.getAuthorName());
        author.setBookList(new ArrayList<>());
        Author savedAuthor = authorRepository.save(author);
        return AuthorCreateResponseBuilder.createResponseDto(savedAuthor);
    }
}
