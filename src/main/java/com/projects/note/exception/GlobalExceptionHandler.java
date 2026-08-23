package com.projects.note.exception;

import com.projects.note.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                "USER_NOT_FOUND", ex.getMessage()
        ));
    }

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoteNotFoundException(NoteNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("NOTE_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(NotebookNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotebookNotFoundException(NotebookNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("NOTEBOOK_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorizedUserException(UnauthorizedUserException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO("UNAUTHORIZED_USER", ex.getMessage()));
    }
}
