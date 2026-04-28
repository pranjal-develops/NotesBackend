package com.projects.note.service;

import com.projects.note.entity.Note;
import com.projects.note.repository.NotesRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class NotesServiceTest {

    @Mock
    private NotesRepo notesRepo;

    @InjectMocks
    private NotesService notesService;

    private Note note1;
    private Note note2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        note1 = new Note();
        note1.setId(1L);
        note1.setTitle("First Note");
        note1.setDescription("This is the first note.");

        note2 = new Note();
        note2.setId(2L);
        note2.setTitle("Second Note");
        note2.setDescription("This is the second note.");
    }

    @Test
    void testCreateNote() {

        when(notesRepo.save(note1)).thenReturn(note1);

        Note createdNote = notesService.createNote(note1);

        assertEquals(note1, createdNote);
        verify(notesRepo, times(1)).save(note1);
    }

    @Test
    void testGetAllNotes() {
        when(notesRepo.findAll()).thenReturn(Arrays.asList(note1, note2));
        List<Note> notes = notesService.getAllNotes();

        assertEquals(2, notes.size());
        assertEquals("First Note", notes.get(0).getTitle());
        assertEquals("Second Note", notes.get(1).getTitle());
        verify(notesRepo, times(1)).findAll();
    }

    @Test
    void testGetNoteById() {
        Long noteId = 1L;

        when(notesRepo.findById(noteId)).thenReturn(Optional.of(note1));

        Optional<Note> foundNote = notesService.getNoteById(noteId);

        assertTrue(foundNote.isPresent());
        assertEquals(note1, foundNote.get());
        verify(notesRepo, times(1)).findById(noteId);
    }

    @Test
    void testDeleteNote() {
        Long noteId = 1L;
        notesService.deleteNote(noteId);
        verify(notesRepo, times(1)).deleteById(noteId); //verify() is a method used to check if certain interactions were made with mock objects. It's a crucial component for unit testing as it allows you to assert that specific methods were called on the mocked instances, helping ensure that your code behaves as expected.
    }
}
