package com.projects.note.service;

import com.projects.note.dto.DrawingDto;
import com.projects.note.dto.NoteDTO;
import com.projects.note.entity.Note;
import com.projects.note.entity.User;
import com.projects.note.enums.Role;
import com.projects.note.enums.ShareRole;
import com.projects.note.exception.NoteNotFoundException;
import com.projects.note.exception.UnauthorizedUserException;
import com.projects.note.exception.UserNotFoundException;
import com.projects.note.repository.NotesRepo;
import com.projects.note.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotesService {
    private final NotesRepo noteRepo;
    private final UserRepo userRepo;

    private User getCurrentUser() {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        return userRepo.findByUsername(username).orElseThrow(() -> {
            log.warn("User not found");
            return new UserNotFoundException("User not found");
        });
    }

    public NoteDTO createNote(NoteDTO noteDTO) {
        User user = getCurrentUser();
        Note note = new Note(
                noteDTO.getTitle(),
                noteDTO.getDescription(),
                noteDTO.getDrawingData(),
                noteDTO.getColor(),
                noteDTO.isPinned(),
                noteDTO.getTags(),
                noteDTO.isDrawing(),
                user
        );
        return convertToNoteDTO(noteRepo.save(note));
    }

    public List<NoteDTO> getAllNotes() {
        User user = getCurrentUser();
        return user.getRole() == Role.ADMIN ?
                ((List<Note>) noteRepo.findAll()).stream().map(this::convertToNoteDTO).toList() :
                noteRepo.findByUser(user).stream().map(this::convertToNoteDTO).toList();
        // since findAll is returning Iterable<Note>, we are writing it as ((List <Note>) noteRepo.findAll())
    }

    public NoteDTO getNoteById(Long id) {
        User user = getCurrentUser();
        Note note = noteRepo.findById(id).orElseThrow(() -> {
            log.warn("Note not found for note id: {}", id);
            return new NoteNotFoundException("Note not found for id: " + id);
        });
        ShareRole userRole = note.getCollaboration().get(user);
        boolean isAuthorized = user.getRole() == Role.ADMIN ||
                note.getOwner() == user ||
                userRole == ShareRole.EDITOR ||
                userRole == ShareRole.READER ||
                note.isPublic();
        if (!isAuthorized) {
            throw new UnauthorizedUserException("You are not authorized to be here. I don't know how you ended up in this place");
        }
        return convertToNoteDTO(note);
    }

    public void deleteNote(Long id) {
        User user = getCurrentUser();
        Note note = noteRepo.findById(id).orElseThrow(() -> {
            log.warn("Note not found for note id: {}", id);
            return new NoteNotFoundException("Note not found for id: " + id);
        });
        if (user.getRole() == Role.ADMIN || note.getOwner() == user) noteRepo.deleteById(id);
    }


    public List<NoteDTO> search(String keyword) {
        User user = getCurrentUser();
        if (user.getRole() == Role.ADMIN) {
            return noteRepo.findByKeyword(keyword).stream()
                    .map(this::convertToNoteDTO)
                    .collect(Collectors.toList());
        } else {
            return noteRepo.searchByUser(user, keyword).stream().map(this::convertToNoteDTO).toList();
        }
    }

    public List<NoteDTO> getByTag(String tag) {
        User user = getCurrentUser();
        return user.getRole() == Role.ADMIN ?
                noteRepo.findByTagsContaining(tag).stream().map(this::convertToNoteDTO).toList() :
                noteRepo.findByOwnerAndTagsContaining(user, tag).stream().map(this::convertToNoteDTO).toList();
    }

    public NoteDTO updateNote(long id, NoteDTO noteDTO) {
        User user = getCurrentUser();
        Note old = noteRepo.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
        if (user == old.getOwner() || old.getCollaboration().get(user) == ShareRole.EDITOR) {
            old.setTitle(
                    noteDTO.getTitle() != null && !noteDTO.getTitle().isEmpty()
                            ? noteDTO.getTitle()
                            : old.getTitle());
            old.setDescription(
                    noteDTO.getDescription() != null && !noteDTO.getDescription().isEmpty()
                            ? noteDTO.getDescription()
                            : old.getDescription());
            old.setDrawingData(
                    noteDTO.getDrawingData() != null
                            ? noteDTO.getDrawingData()
                            : old.getDrawingData());
            old.setColor(
                    noteDTO.getColor() != null
                            ? noteDTO.getColor()
                            : old.getColor());
            old.setUpdatedDate(OffsetDateTime.now());
            old.setPinned(noteDTO.isPinned());
            old.setTags(noteDTO.getTags());
            old.setDrawing(noteDTO.isDrawing());
            return convertToNoteDTO(noteRepo.save(old));
        }
        throw new UnauthorizedUserException("You are not authorized to be here. I don't know how you ended up in this place");
    }

    private NoteDTO convertToNoteDTO(Note note) {
        return new NoteDTO(
                note.getId(),
                note.getTitle(),
                note.getDescription(),
                note.getCreatedDate(),
                note.getUpdatedDate(),
                note.getDrawingData(),
                note.getColor(),
                note.isPinned(),
                note.getTags(),
                note.isDrawing(),
                note.getOwner(),
                note.getCollaboration() != null ? note.getCollaboration().entrySet().stream().collect(Collectors.toMap(e -> e.getKey().getId(), Map.Entry::getValue)) : new java.util.HashMap<>()
        );
    }

    public List<DrawingDto> getAllDrawings() {
        User user = getCurrentUser();
        return user.getRole() == Role.ADMIN ?
                noteRepo.findByIsDrawingTrue().stream().map(this::convertToDrawingDto).toList() :
                noteRepo.findByOwnerAndIsDrawingTrue(user).stream().map(this::convertToDrawingDto).toList();

    }

    public List<DrawingDto> getDrawingsByTag(String tag) {
        User user = getCurrentUser();
        return user.getRole() == Role.ADMIN ?
                noteRepo.findByIsDrawingAndTagsContaining(true, tag).stream().map(this::convertToDrawingDto).toList() :
                noteRepo.findByOwnerAndIsDrawingAndTagsContaining(user, true, tag).stream().map(this::convertToDrawingDto).toList();
    }

    private DrawingDto convertToDrawingDto(Note note) {
        return new DrawingDto(
                note.getId(),
                note.getCreatedDate(),
                note.getUpdatedDate(),
                note.getDrawingData(),
                note.getColor(),
                note.isPinned(),
                note.getTags(),
                note.getOwner(),
                note.getCollaboration() != null ? note.getCollaboration().entrySet().stream().collect(Collectors.toMap(e -> e.getKey().getId(), Map.Entry::getValue)) : new java.util.HashMap<>()
        );
    }
}
