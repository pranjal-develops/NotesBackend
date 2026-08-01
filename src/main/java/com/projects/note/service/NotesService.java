package com.projects.note.service;

import com.projects.note.dto.DrawingDto;
import com.projects.note.dto.NoteDTO;
import com.projects.note.entity.Note;
import com.projects.note.repository.NotesRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class NotesService {
    @Autowired
    private NotesRepo noteRepo;

    public NoteDTO createNote(NoteDTO noteDTO) {
        Note note = new Note(
                noteDTO.getTitle(),
                noteDTO.getDescription(),
                noteDTO.getDrawingData(),
                noteDTO.getColor(),
                noteDTO.isPinned(),
                noteDTO.getTags(),
                noteDTO.isDrawing()
        );
        return convertToNoteDTO(noteRepo.save(note));
    }

    public List<NoteDTO> getAllNotes() {
        return ((List<Note>) noteRepo.findAll()).stream().map(this::convertToNoteDTO).toList(); // since findAll is returning Iterable<Note>, we are writing it as ((List <Note>) noteRepo.findAll())
    }

    public Optional<NoteDTO> getNoteById(Long id) {
        return (noteRepo.findById(id).map(this::convertToNoteDTO));
    }

    public void deleteNote(Long id) {
        noteRepo.deleteById(id);
    }


    public List<NoteDTO> search(String keyword) {
        return noteRepo.findByKeyword(keyword).stream()
                .map(this::convertToNoteDTO)
                .collect(Collectors.toList());
    }

    public List<NoteDTO> getByTag(String tag) {
        return noteRepo.findByTagsContaining(tag).stream().map(this::convertToNoteDTO).toList();
    }

    public NoteDTO updateNote(long id, NoteDTO noteDTO) {
        Note old = noteRepo.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
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
                note.isDrawing()
        );
    }

    public List<DrawingDto> getAllDrawings() {
        return ((List<Note>) noteRepo.findByIsDrawingTrue()).stream().map(this::convertToDrawingDto).toList(); // since findAll is returning Iterable<Note>, we are writing it as ((List <Note>) noteRepo.findAll())

    }

    public List<DrawingDto> getDrawingsByTag(String tag) {
        return noteRepo.findByIsDrawingAndTagsContaining(true, tag).stream().map(this::convertToDrawingDto).toList();
    }

    private DrawingDto convertToDrawingDto(Note note) {
        return new DrawingDto(
                note.getId(),
                note.getCreatedDate(),
                note.getUpdatedDate(),
                note.getDrawingData(),
                note.getColor(),
                note.isPinned(),
                note.getTags()
        );
    }
}
