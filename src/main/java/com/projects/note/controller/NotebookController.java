package com.projects.note.controller;

import com.projects.note.dto.NotebookDTO;
import com.projects.note.service.NotebookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notebooks")
@RequiredArgsConstructor
public class NotebookController {
    private final NotebookService notebookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NotebookDTO> getAll() {
        return notebookService.getAllNotebooks();
    }

    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<NotebookDTO> getByID(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(notebookService.getNotebookById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotebookDTO create(@RequestBody NotebookDTO notebookDTO) {
        return notebookService.createNotebook(notebookDTO);
    }

    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<NotebookDTO> update(@PathVariable Long id, @RequestBody NotebookDTO notebookDTO) {
        try {
            return ResponseEntity.ok(notebookService.updateNotebook(id, notebookDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        notebookService.deleteNotebook(id);
    }
}
