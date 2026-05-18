package com.projects.note.repository;

import com.projects.note.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepo extends JpaRepository<Page, Long> {
}
