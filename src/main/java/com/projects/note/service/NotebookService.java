package com.projects.note.service;

import com.projects.note.dto.NotebookDTO;
import com.projects.note.dto.PageDTO;
import com.projects.note.dto.PageSummaryDTO;
import com.projects.note.entity.Notebook;
import com.projects.note.entity.Page;
import com.projects.note.entity.User;
import com.projects.note.enums.Role;
import com.projects.note.enums.ShareRole;
import com.projects.note.exception.NoteNotFoundException;
import com.projects.note.exception.NotebookNotFoundException;
import com.projects.note.exception.UnauthorizedUserException;
import com.projects.note.exception.UserNotFoundException;
import com.projects.note.repository.NotebookRepo;
import com.projects.note.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotebookService {
    private final NotebookRepo notebookRepo;
    private final UserRepo userRepo;

    private User getCurrentUser() {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        return userRepo.findByUsername(username).orElseThrow(() -> {
            log.warn("User not found");
            return new UserNotFoundException("User not found");
        });
    }

    public List<NotebookDTO> getAllNotebooks() {
        User user = getCurrentUser();
        return user.getRole() == Role.ADMIN ?
                notebookRepo.findAll().stream().map(this::convertToDTO).toList()
                : notebookRepo.findByUser(user).stream().map(this::convertToDTO).toList();
        //this::convertToDTO is the same as notebook->convertToDTO(notebook)
    }

    public NotebookDTO getNotebookById(Long id) {
        Notebook notebook = notebookRepo.findById(id).orElseThrow(() -> {
            log.warn("Notebook not found for notebook id: {}", id);
            return new NotebookNotFoundException("Notebook not found for id: " + id);
        });
        User user = getCurrentUser();
        ShareRole role = notebook.getCollaboration().get(user);
        if (user.getRole() == Role.ADMIN || notebook.getOwner() == user || role != null || notebook.isPublic()) {
            return convertToDTO(notebook);
        }
        throw new UnauthorizedUserException("No access to this notebook");

    }

    @Transactional
    public NotebookDTO createNotebook(NotebookDTO dto) {
        Notebook notebook = new Notebook();
        notebook.setName(dto.getName());
        notebook.setDescription(dto.getDescription());
        notebook.setColor(dto.getColor());
        notebook.setLogo(dto.getLogo());
        notebook.setOwner(getCurrentUser());
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
        Notebook old = notebookRepo.findById(id).orElseThrow(() -> {
            log.warn("Notebook not found for notebook id: {}", id);
            return new NotebookNotFoundException("Notebook not found for id: " + id);
        });
        User user = getCurrentUser();
        ShareRole role = old.getCollaboration().get(user);
        if (user.getRole() == Role.ADMIN || old.getOwner() == user || role == ShareRole.EDITOR) {
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
        } else {
            throw new UnauthorizedUserException("Not authorized to update this message");
        }
    }

    @Transactional
    public void reorderPages(Long notebookId, List<Long> pageIds) {
        Notebook notebook = notebookRepo.findById(notebookId).orElseThrow(() -> {
            log.warn("Notebook not found for notebook id: {}", notebookId);
            return new NotebookNotFoundException("Notebook not found for id: " + notebookId);
        });
        User user = getCurrentUser();
        ShareRole role = notebook.getCollaboration().get(user);
        if (user.getRole() == Role.ADMIN || notebook.getOwner() == user || role == ShareRole.EDITOR) {
            notebook.getPages().forEach(page -> {
                int index = pageIds.indexOf(page.getId());
                if (index != -1) {
                    page.setPageOrder(index);
                }
            });

            notebookRepo.save(notebook);
        } else {
            throw new UnauthorizedUserException("Not authorized to update this message");
        }
    }

    @Transactional
    public void deleteNotebook(Long id) {
        Notebook old = notebookRepo.findById(id).orElseThrow(() -> new RuntimeException("Notebook not found"));
        User user = getCurrentUser();
        if (user.getRole() == Role.ADMIN || old.getOwner() == user) {
            notebookRepo.delete(old);
        } else {
            throw new UnauthorizedUserException("Only the owner can delete this notebook");
        }
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
                pageSummaries,
                notebook.getOwner(),
                notebook.getCollaboration() != null ? notebook.getCollaboration().entrySet().stream().collect(java.util.stream.Collectors.toMap(e -> e.getKey().getId(), java.util.Map.Entry::getValue)) : new java.util.HashMap<>()
        );
    }

}
