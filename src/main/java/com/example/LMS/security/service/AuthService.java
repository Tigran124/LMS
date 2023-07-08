package com.example.LMS.security.service;

import com.example.LMS.entity.Role;
import com.example.LMS.entity.User;
import com.example.LMS.repository.UserRepository;
import com.example.LMS.security.dto.AuthRequestDto;
import com.example.LMS.security.dto.AuthResponseDto;
import com.example.LMS.security.dto.RegRequestDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getUsername(),
                        authRequestDto.getPassword()
                ));
        User user = (User) repository.findByUsername(authRequestDto.getUsername())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setToken(jwtToken);
        return authResponseDto;
    }

    public AuthResponseDto register(RegRequestDto regRequestDto) {
        User user = new User();
        user.setUsername(regRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(regRequestDto.getPassword()));
        user.setRole(Role.USER);
        if (patternMatches(regRequestDto.getEmail())){
            user.setEmail(regRequestDto.getEmail());
        }else {
            throw new UsernameNotFoundException("Invalid input");
        }
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setToken(jwtToken);
        return authResponseDto;
    }

    private boolean patternMatches(String emailAddress) {
        return Pattern.compile("^(.+)@(.+)$")
                .matcher(emailAddress)
                .matches();
    }
}
