package com.projects.note.service;

import com.projects.note.entity.Note;
import com.projects.note.repository.NotesRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class NotesService {
    @Autowired
    private NotesRepo noteRepo;

    public Note createNote(Note note) {
        return noteRepo.save(note);
    }

    public List<Note> getAllNotes() {
        return (List<Note>) noteRepo.findAll();
    }

    public Optional<Note> getNoteById(Long id) {
        return noteRepo.findById(id);
    }

    public void deleteNote(Long id) {
        noteRepo.deleteById(id);
    }
}
