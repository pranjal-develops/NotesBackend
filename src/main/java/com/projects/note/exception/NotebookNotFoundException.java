package com.projects.note.exception;

public class NotebookNotFoundException extends RuntimeException {
    public NotebookNotFoundException(String message) {
        super(message);
    }
}
