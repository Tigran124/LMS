package com.example.LMS.service;

import com.example.LMS.builder.author.AuthorCreateResponseBuilder;
import com.example.LMS.builder.author.AuthorResponseBuilder;
import com.example.LMS.dto.author.AuthorCreateRequestDto;
import com.example.LMS.dto.author.AuthorCreateResponseDto;
import com.example.LMS.dto.author.AuthorResponseDto;
import com.example.LMS.entity.Author;
import com.example.LMS.exception.NoContentToDeleteException;
import com.example.LMS.exception.ResourceNotFoundException;
import com.example.LMS.repository.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookService bookService;

    public AuthorService(AuthorRepository authorRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

    public AuthorCreateResponseDto createAuthor(AuthorCreateRequestDto requestDto) {
        Author author = new Author();
        author.setAuthorName(requestDto.getAuthorName());
        author.setBookList(new ArrayList<>());
        Author savedAuthor = authorRepository.save(author);
        return AuthorCreateResponseBuilder.createResponseDto(savedAuthor);
    }

    public List<AuthorResponseDto> getAllAuthors(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authorPage = authorRepository.findAll(pageable);
        if (authorPage.hasContent()) {
            return authorPage.getContent().stream()
                    .map(AuthorResponseBuilder::buildAuthorResponseDto)
                    .toList();
        } else return new ArrayList<>();
    }

    public AuthorResponseDto getAuthorById(Long id) {
        Author author = getAuthor(id);
        return AuthorResponseBuilder.buildAuthorResponseDto(author);
    }

    public void deleteAuthorById(Long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isEmpty()) {
            throw new NoContentToDeleteException("Author not found to delete");
        }
        authorRepository.deleteById(id);
    }

    private Author getAuthor(Long authorId) {
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isEmpty()) {
            throw new ResourceNotFoundException("Author not found");
        }
        return optionalAuthor.get();
    }
}
