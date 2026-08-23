package com.projects.note.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
