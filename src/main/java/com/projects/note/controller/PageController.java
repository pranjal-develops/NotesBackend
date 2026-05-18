package com.projects.note.controller;

import com.projects.note.dto.PageDTO;
import com.projects.note.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notebooks/{notebookId}/pages")
@RequiredArgsConstructor
public class PageController {
    private final PageService pageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PageDTO addPage(@PathVariable Long notebookId, @RequestBody PageDTO dto) {
        return pageService.addPageToNotebook(notebookId, dto);
    }

    @PutMapping("/{pageId}")
    public PageDTO updatePage(@PathVariable Long pageId, @RequestBody PageDTO dto) {
        return pageService.updatePage(pageId, dto);
    }
}
