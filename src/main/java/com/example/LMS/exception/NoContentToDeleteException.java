package com.example.LMS.exception;

public class NoContentToDeleteException extends RuntimeException{

    public NoContentToDeleteException(String message) {
        super(message);
    }
}
