package com.projects.note.service;

import com.projects.note.dto.NotebookDTO;
import com.projects.note.dto.PageDTO;
import com.projects.note.dto.PageSummaryDTO;
import com.projects.note.entity.Notebook;
import com.projects.note.entity.Page;
import com.projects.note.repository.NotebookRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotebookService {
    private final NotebookRepo notebookRepo;

    public List<NotebookDTO> getAllNotebooks() {
        return notebookRepo.findAll().stream().map(this::convertToDTO).toList();
        //this::convertToDTO is the same as notebook->convertToDTO(notebook)
    }

    public NotebookDTO getNotebookById(Long id) {
        return notebookRepo.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Notebook not found"));
    }

    @Transactional
    public NotebookDTO createNotebook(NotebookDTO dto) {
        Notebook notebook = new Notebook();
        notebook.setName(dto.getName());
        notebook.setDescription(dto.getDescription());
        notebook.setColor(dto.getColor());
        notebook.setLogo(dto.getLogo());
        final Notebook savedNotebook = notebookRepo.save(notebook);

        if (dto.getPages() != null && !dto.getPages().isEmpty()) {
            List<Page> initialPages = dto.getPages().stream().map(summaryDto -> {
                Page page = new Page();
                page.setTitle(summaryDto.getTitle());
                // page.setContent("");
                page.setContentHtml("");
                page.setPageOrder(summaryDto.getPageOrder());
                page.setNotebook(savedNotebook);
                return page;
            }).toList();
            // We need to save the pages explicitly or ensure CascadeType.ALL is on the entity
            savedNotebook.getPages().addAll(initialPages);
            return convertToDTO(notebookRepo.save(savedNotebook));

        }
        return convertToDTO(notebookRepo.save(notebook));
    }

    @Transactional
    public NotebookDTO updateNotebook(Long id, NotebookDTO dto) {
        Notebook old = notebookRepo.findById(id).orElseThrow(() -> new RuntimeException("Notebook not found"));
        old.setName(
                dto.getName() != null && !dto.getName().isEmpty() ?
                        dto.getName() :
                        old.getName()
        );
        old.setDescription(
                dto.getDescription() != null
                        ? dto.getDescription()
                        : old.getDescription());
        old.setColor(
                dto.getColor() != null ?
                        dto.getColor() :
                        old.getColor()
        );
        old.setLogo(
                dto.getLogo() != null ?
                        dto.getLogo() :
                        old.getLogo()
        );
        old.setUpdatedDate(OffsetDateTime.now());
        return convertToDTO(notebookRepo.save(old));
    }

    @Transactional
    public void deleteNotebook(Long id) {
        notebookRepo.deleteById(id);
    }

    private NotebookDTO convertToDTO(Notebook notebook) {
        List<PageSummaryDTO> pageSummaries = notebook.getPages().stream().map(page -> new PageSummaryDTO(page.getId(),
                        page.getTitle(),
                        page.getPageOrder()))
                .toList();
        return new NotebookDTO(
                notebook.getId(),
                notebook.getName(),
                notebook.getDescription(),
                notebook.getColor(),
                notebook.getCreatedDate(),
                notebook.getUpdatedDate(),
                notebook.getLogo(),
                pageSummaries
        );
    }

}
