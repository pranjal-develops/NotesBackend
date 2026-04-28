package com.projects.note.repository;

import com.projects.note.entity.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotesRepo extends CrudRepository<Note, Long> {

    //    List<Note> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String keyword1, String keyword2);
    //Same Thing
    @Query("Select n from Note n WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(n.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Note> findByKeyword(String keyword);
}
