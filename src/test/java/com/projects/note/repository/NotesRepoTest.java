package com.projects.note.repository;

import com.projects.note.entity.Note;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NotesRepoTest {

    @Autowired
    private NotesRepo notesRepo;


    @Test
    @Rollback(false)
    public void testSaveAndFindNote(){
        Note note = new Note();
        note.setTitle("Test Title");
        note.setDescription("Test Desc");

        Note savedNote= notesRepo.save(note);
        Optional<Note> findNote = notesRepo.findById(savedNote.getId());
        assertThat(findNote).isPresent();
        assertThat(findNote.get().getTitle()).isEqualTo("Test Title");
        assertThat(findNote.get().getDescription()).isEqualTo("Test Desc");
    }

    @Test
    void testDeleteNote(){
        Note note = new Note();
        note.setTitle("Test Title");
        note.setDescription("Test Desc");

        Note savedNote= notesRepo.save(note);
        notesRepo.deleteById(savedNote.getId());
        assertThat(notesRepo.findById(savedNote.getId())).isNotPresent();
    }
}
