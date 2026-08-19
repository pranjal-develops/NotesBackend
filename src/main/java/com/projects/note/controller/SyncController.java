package com.projects.note.controller;

import com.projects.note.dto.SyncRequestDTO;
import com.projects.note.service.NotesService;
import com.projects.note.service.NotebookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sync")
@RequiredArgsConstructor
public class SyncController {
    private final NotesService notesService;
    private final NotebookService notebookService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void syncData(@RequestBody SyncRequestDTO request) {
        if (request.getNotebooks() != null) {
            for (var notebookDto : request.getNotebooks()) {
                notebookService.createNotebook(notebookDto);
            }
        }
        if (request.getNotes() != null) {
            for (var noteDto : request.getNotes()) {
                notesService.createNote(noteDto);
            }
        }
    }
}
