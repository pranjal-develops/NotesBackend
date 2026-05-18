package com.projects.note.repository;

import com.projects.note.entity.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotebookRepo extends JpaRepository<Notebook, Long> {
}
