package com.example.LMS.service;

import com.example.LMS.builder.author.AuthorCreateResponseBuilder;
import com.example.LMS.builder.author.AuthorResponseBuilder;
import com.example.LMS.builder.book.BookResponseBuilder;
import com.example.LMS.dto.author.AuthorCreateRequestDto;
import com.example.LMS.dto.author.AuthorCreateResponseDto;
import com.example.LMS.dto.author.AuthorResponseDto;
import com.example.LMS.dto.author.AuthorUnitResponseDto;
import com.example.LMS.entity.Author;
import com.example.LMS.entity.Book;
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
    private final BookService bookService;

    public AuthorService(AuthorRepository authorRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
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
                        .map((Book book) -> BookResponseBuilder.buildBookResponseDto(book, bookService
                                .calculateRate(book)))
                        .collect(Collectors.toList())
        );
        return responseDto;
    }

    public Boolean deleteAuthorById(Long id){
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isEmpty()){
            return false;
        }
        authorRepository.deleteById(id);
        return true;
    }
}
