package com.example.LMS.controller;

import com.example.LMS.dto.author.AuthorCreateRequestDto;
import com.example.LMS.dto.author.AuthorCreateResponseDto;
import com.example.LMS.dto.author.AuthorResponseDto;
import com.example.LMS.service.AuthorService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    @Secured("ADMIN")
    public AuthorCreateResponseDto createAuthor(@RequestBody AuthorCreateRequestDto requestDto) {
        return authorService.createAuthor(requestDto);
    }

    @GetMapping("/all")
    public List<AuthorResponseDto> getAllAuthors(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        return authorService.getAllAuthors(page, size);
    }

    @GetMapping("/{id}")
    public AuthorResponseDto getAuthorById(@PathVariable(value = "id") Long id) {
        return authorService.getAuthorById(id);
    }

    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    public void deleteAuthorById(@PathVariable(value = "id") Long id) {
        authorService.deleteAuthorById(id);
    }
}
