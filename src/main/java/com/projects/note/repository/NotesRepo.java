package com.projects.note.repository;

import com.projects.note.entity.Note;
import org.springframework.data.repository.CrudRepository;

public interface NotesRepo extends CrudRepository<Note, Long> {
}
