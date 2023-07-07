package com.example.LMS.service;

import com.example.LMS.builder.AuthorCreateResponseBuilder;
import com.example.LMS.builder.AuthorResponseBuilder;
import com.example.LMS.builder.BookResponseBuilder;
import com.example.LMS.dto.AuthorCreateRequestDto;
import com.example.LMS.dto.AuthorCreateResponseDto;
import com.example.LMS.dto.AuthorResponseDto;
import com.example.LMS.dto.AuthorUnitResponseDto;
import com.example.LMS.entity.Author;
import com.example.LMS.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<AuthorResponseDto> getAllAuthors(){
        return authorRepository.findAll()
                .stream()
                .map(AuthorResponseBuilder::buildAuthorResponseDto)
                .collect(Collectors.toList());
    }

    public AuthorUnitResponseDto getAuthorById(Long id){
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()){
            throw new RuntimeException();
        }
        AuthorUnitResponseDto responseDto = new AuthorUnitResponseDto();
        responseDto.setId(id);
        responseDto.setAuthorName(author.get().getAuthorName());
        responseDto.setBookResponseDtoList(
                author.get()
                        .getBookList()
                        .stream()
                        .map(BookResponseBuilder::buildBookResponseDto)
                        .collect(Collectors.toList())
        );
        return responseDto;
    }
}
