package com.example.LMS.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {

    private String username;
    private String password;
}
