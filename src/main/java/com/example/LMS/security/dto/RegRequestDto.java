package com.example.LMS.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegRequestDto {

    private String username;
    private String password;
    private String email;
}
