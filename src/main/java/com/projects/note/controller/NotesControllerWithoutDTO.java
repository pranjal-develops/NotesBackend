//package com.projects.note.controller;
//
//import com.projects.note.dto.NoteDTO;
//import com.projects.note.entity.Note;
//import com.projects.note.service.NotesService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/notes")
//
//public class NotesControllerWithoutDTO {
//    @Autowired
//    private NotesService noteService;
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<NoteDTO> getAll() {return noteService.getAllNotes()}
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getById(@PathVariable Long id) {
//        Optional<Note> note = noteService.getNoteById(id);
//        return note.isPresent()
//                ? new ResponseEntity<>(note, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getById(@PathVariable Long id) {
//        Optional<Note> note = noteService.getNoteById(id);
//        return note.isPresent()
//                ? new ResponseEntity<>(note, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Note create(@RequestBody Note note) {
//        if (note.getDescription() != null && note.getDescription().length() > 255) {
//            // Truncate the description to 255 characters
//            note.setDescription(note.getDescription().substring(0, 255));
//        }
//        return noteService.createNote(note);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Note note) {
//        Note old = noteService.getNoteById(id).orElse(null);
//        if (old != null) {
//            old.setTitle(
//                    note.getTitle() != null && !note.getTitle().isEmpty()
//                            ? note.getTitle()
//                            : old.getTitle());
//            old.setDescription(
//                    note.getDescription() != null && !note.getDescription().isEmpty()
//                            ? note.getDescription()
//                            : old.getDescription());
//            Note updatedNote = noteService.createNote(old); // Save the updated note
//            return ResponseEntity.ok(updatedNote);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable Long id) {
//        noteService.deleteNote(id);
//    }
//}
