package com.example.LMS.exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Getter
public class ApiError {

    private HttpStatus httpStatus;
    private String message;
    private LocalDateTime localDateTime;

    public ApiError(HttpStatus httpStatus, String message, LocalDateTime localDateTime) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.localDateTime = localDateTime;
    }
}
