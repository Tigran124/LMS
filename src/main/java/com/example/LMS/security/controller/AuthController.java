package com.example.LMS.security.controller;

import com.example.LMS.security.service.AuthService;
import com.example.LMS.security.dto.AuthRequestDto;
import com.example.LMS.security.dto.AuthResponseDto;
import com.example.LMS.security.dto.RegRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto authRequestDto) {
        return authService.login(authRequestDto);
    }

    @PostMapping("/register")
    public AuthResponseDto register(@RequestBody RegRequestDto regRequestDto) {
        return authService.register(regRequestDto);
    }
}
