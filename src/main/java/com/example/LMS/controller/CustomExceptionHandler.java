package com.example.LMS.controller;

import com.example.LMS.exception.ApiError;
import com.example.LMS.exception.NoContentToDeleteException;
import com.example.LMS.exception.ResourceNotFoundException;
import com.example.LMS.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex){
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiError> handleValidationException(ValidationException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(NoContentToDeleteException.class)
    public ResponseEntity<ApiError> handleNoContentToDeleteException(NoContentToDeleteException ex){
        ApiError apiError = new ApiError(HttpStatus.NO_CONTENT, ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }
}
