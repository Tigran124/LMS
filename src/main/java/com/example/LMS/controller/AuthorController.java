package com.example.LMS.controller;

import com.example.LMS.dto.AuthorCreateRequestDto;
import com.example.LMS.dto.AuthorCreateResponseDto;
import com.example.LMS.service.AuthorService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/create")
    @Secured("ADMIN")
    public AuthorCreateResponseDto createAuthor(@RequestBody AuthorCreateRequestDto requestDto) {
        return authorService.createAuthor(requestDto);
    }
}
