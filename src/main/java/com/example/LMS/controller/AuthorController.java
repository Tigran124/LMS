package com.example.LMS.controller;

import com.example.LMS.dto.author.AuthorCreateRequestDto;
import com.example.LMS.dto.author.AuthorCreateResponseDto;
import com.example.LMS.dto.author.AuthorResponseDto;
import com.example.LMS.dto.author.AuthorUnitResponseDto;
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

    @PostMapping("/create")
    @Secured("ADMIN")
    public AuthorCreateResponseDto createAuthor(@RequestBody AuthorCreateRequestDto requestDto) {
        return authorService.createAuthor(requestDto);
    }

    @GetMapping("/getAll")
    public List<AuthorResponseDto> getAllAuthors(){
        return authorService.getAllAuthors();
    }

    @GetMapping("/get/{id}")
    public AuthorUnitResponseDto getAuthorById(@PathVariable(value = "id") Long id){
        return authorService.getAuthorById(id);
    }
}
