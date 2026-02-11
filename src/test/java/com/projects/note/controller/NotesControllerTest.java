package com.projects.note.controller;


import com.projects.note.dto.NoteDTO;
import com.projects.note.entity.Note;
import com.projects.note.service.NotesService;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(NotesController.class)
public class NotesControllerTest {
    @MockitoBean
    private NotesService notesService;

    @Autowired
    private NotesController notesController;

    @Test
    public void testGetAllNotes() {
        Note note1 = new Note();
        note1.setId(1L);
        note1.setTitle("title1");
        note1.setDescription("description1");

        Note note2 = new Note();
        note2.setId(2L);
        note2.setTitle("title2");
        note2.setDescription("description2");

        when(notesService.getAllNotes()).thenReturn(Arrays.asList(note1, note2));

        List<NoteDTO> notes = notesController.getAll();

        assertEquals(2, notes.size());
        assertEquals("title1", notes.get(0).getTitle());
        assertEquals("title2", notes.get(1).getTitle());
    }

    @Test
    public void testGetNoteByIDFound() {
        Long noteId = 1L;
        Note note = new Note();
        note.setId(noteId);
        note.setTitle("title");
        note.setDescription("description");

        when(notesService.getNoteById(noteId)).thenReturn(Optional.of(note));

        ResponseEntity<?> response = notesController.getById(noteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        NoteDTO noteDTO = (NoteDTO) response.getBody();
        assertNotNull(noteDTO);
        assertEquals(noteId, noteDTO.getId());
    }

    @Test
    void testGetNoteByIdNotFound() {
        Long noteId = 1L;
        when(notesService.getNoteById(noteId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = notesController.getById(noteId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateNote() {
        NoteDTO noteDTO = new NoteDTO(null, "title", "description");
        Note note = new Note();
        note.setTitle(noteDTO.getTitle());
        note.setDescription(noteDTO.getDescription());

        when(notesService.createNote(any(Note.class))).thenReturn(note);

        NoteDTO createdNote = notesController.create(noteDTO);
        assertNotNull(createdNote);
        assertEquals("title", createdNote.getTitle());
        assertEquals("description", createdNote.getDescription());
        verify(notesService, times(1)).createNote(any(Note.class));
    }



    @Test
    void testUpdateNoteFound() {

    Long noteId = 1L;
    NoteDTO noteDTO = new NoteDTO(null, "Updated Note", "Updated description");
    Note note = new Note();
    note.setId(noteDTO.getId());
    note.setTitle("Old Title");
    note.setDescription("Old Description");

    when(notesService.getNoteById(noteId)).thenReturn(Optional.of(note));
    when(notesService.createNote(any(Note.class))).thenReturn(note);

    ResponseEntity<?> response = notesController.update(noteId,noteDTO);

    assertEquals(HttpStatus.OK,response.getStatusCode());
    NoteDTO updatedNoteDTO =  (NoteDTO) response.getBody();
    assertNotNull(updatedNoteDTO);
    assertEquals("Updated Note", updatedNoteDTO.getTitle());
    assertEquals("Updated description", updatedNoteDTO.getDescription());
    verify(notesService, times(1)).createNote(any(Note.class));

    }

    @Test
    void testUpdateNotFound() {
        Long noteId = 1L;
        NoteDTO noteDTO = new NoteDTO(null, "Updated Note", "Updated description");

        // Simulating the note not found
        when(notesService.getNoteById(noteId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = notesController.update(noteId, noteDTO);

        // Asserting that the response status is NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
