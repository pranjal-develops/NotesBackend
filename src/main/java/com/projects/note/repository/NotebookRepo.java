package com.projects.note.repository;

import com.projects.note.entity.Notebook;
import com.projects.note.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotebookRepo extends JpaRepository<Notebook, Long> {
    @Query("SELECT n FROM Notebook n LEFT JOIN n.collaboration c WHERE n.owner = :user OR KEY(c) = :user")
    List<Notebook> findByUser(@Param("user") User user);
}
