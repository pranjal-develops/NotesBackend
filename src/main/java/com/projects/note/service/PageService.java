package com.projects.note.service;

import com.projects.note.dto.PageDTO;
import com.projects.note.entity.Notebook;
import com.projects.note.entity.Page;
import com.projects.note.repository.NotebookRepo;
import com.projects.note.repository.PageRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class PageService {
    private final PageRepo pageRepo;
    private final NotebookRepo notebookRepo;

    public PageDTO getPageById(Long id) {
        return pageRepo.findById(id).map(this::convertToDto).orElseThrow(() -> new RuntimeException("Page not found"));
    }

    public PageDTO getPageInNotebook(Long notebookId, Long pageId) {
        Page page = pageRepo.findById(pageId).orElseThrow(() -> new RuntimeException("Page not found"));
        if (!page.getNotebook().getId().equals(notebookId)) {
            throw new RuntimeException("This page does not belong to the specific notebook!");
        }
        return convertToDto(page);
    }

    @Transactional
    public PageDTO addPageToNotebook(Long notebookId, PageDTO dto) {
        Notebook notebook = notebookRepo.findById(notebookId)
                .orElseThrow(() -> new RuntimeException("Notebook not present"));
        Page page = new Page();
        page.setTitle(dto.getTitle());
        page.setContentHtml(dto.getContentHtml());
        page.setDrawings(dto.getDrawings());
        page.setCodeBlocks(dto.getCodeBlocks());
        page.setCharts(dto.getCharts());
        page.setImages(dto.getImages());
        page.setNotebook(notebook);
        page.setPageOrder(notebook.getPages().size());
        
        Page savedPage = pageRepo.save(page);
        return convertToDto(savedPage);
    }

    @Transactional
    public PageDTO updatePage(Long pageId, PageDTO dto) {
        Page page = pageRepo.findById(pageId)
                .orElseThrow(() -> new RuntimeException("Page does not exist"));
        page.setTitle(dto.getTitle());
//        page.setContent(dto.getContent());
        page.setContentHtml(dto.getContentHtml());
        page.setDrawings(dto.getDrawings());
        page.setCodeBlocks(dto.getCodeBlocks());
        page.setCharts(dto.getCharts());
        page.setImages(dto.getImages());
        page.setUpdatedDate(OffsetDateTime.now());
        return convertToDto(pageRepo.save(page));
    }

    private PageDTO convertToDto(Page page) {
        return new PageDTO(
                page.getId(),
                page.getTitle(),
                page.getContentHtml(),
                page.getDrawings(),
                page.getCharts(),
                page.getCodeBlocks(),
                page.getImages(),
                page.getPageOrder(),
                page.getCreatedDate(),
                page.getUpdatedDate(),
                page.getNotebook() != null ? page.getNotebook().getId() : null
        );
    }
}
