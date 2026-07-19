package com.projects.note.controller;

import com.projects.note.dto.PageDTO;
import com.projects.note.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notebooks/{notebookId}/pages")
@RequiredArgsConstructor
public class PageController {
    private final PageService pageService;

    @GetMapping("/{pageId}")
    public ResponseEntity<PageDTO> getPage(@PathVariable Long notebookId, @PathVariable Long pageId) {
        try {
            return ResponseEntity.ok(pageService.getPageInNotebook(notebookId, pageId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PageDTO> addPage(@PathVariable Long notebookId, @RequestBody PageDTO dto) {
        try {
            PageDTO created = pageService.addPageToNotebook(notebookId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (e.getMessage().contains("not present")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{pageId}")
    public ResponseEntity<PageDTO> updatePage(@PathVariable Long notebookId, @PathVariable Long pageId, @RequestBody PageDTO dto) {
        try {
            return ResponseEntity.ok(pageService.updatePage(pageId, dto));
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (e.getMessage().contains("does not exist")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
