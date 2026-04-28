package com.projects.note.controller;

import com.projects.note.dto.NoteDTO;
import com.projects.note.entity.Note;
import com.projects.note.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
public class NotesController {
    @Autowired
    private NotesService noteService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
//    public List<NoteDTO> getAll() {return noteService.getAllNotes()}
    public List<NoteDTO> getAll(@RequestParam(required = false) String q) {
        if (q == null || q.isBlank()) {
            return noteService.getAllNotes().stream()
                    .map(note -> new NoteDTO(note.getId(),
                            note.getTitle(),
                            note.getDescription(),
                            note.getCreatedDate(),
                            note.getUpdatedDate()))
                    .collect(Collectors.toList());
        }
        return noteService.search(q).stream()
                .map(note -> new NoteDTO(note.getId(),
                        note.getTitle(),
                        note.getDescription(),
                        note.getCreatedDate(),
                        note.getUpdatedDate()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Note> note = noteService.getNoteById(id);
        return note.map(n -> new ResponseEntity<>(new NoteDTO(n.getId(), n.getTitle(), n.getDescription(), n.getCreatedDate(), n.getUpdatedDate()), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDTO create(@RequestBody NoteDTO noteDTO) {
        Note note = new Note();
        note.setTitle(noteDTO.getTitle());
        note.setDescription(noteDTO.getDescription());
        note.setCreatedDate(OffsetDateTime.now());
        note.setUpdatedDate(OffsetDateTime.now());

        Note createdNote = noteService.createNote(note);
        return new NoteDTO(createdNote.getId(), createdNote.getTitle(), createdNote.getDescription());
    }

    // public Note create(@RequestBody Note note){
    // return noteService.createNote(note);
    // }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody NoteDTO noteDTO) {
        Note old = noteService.getNoteById(id).orElse(null);
        if (old != null) {
            old.setTitle(
                    noteDTO.getTitle() != null && !noteDTO.getTitle().isEmpty()
                            ? noteDTO.getTitle()
                            : old.getTitle());
            old.setDescription(
                    noteDTO.getDescription() != null && !noteDTO.getDescription().isEmpty()
                            ? noteDTO.getDescription()
                            : old.getDescription());
            old.setUpdatedDate(OffsetDateTime.now());
            Note updatedNote = noteService.createNote(old); // Save the updated note
            return ResponseEntity.ok(new NoteDTO(updatedNote.getId(), updatedNote.getTitle(), updatedNote.getDescription(), updatedNote.getCreatedDate(), OffsetDateTime.now()));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        noteService.deleteNote(id);
    }

}
