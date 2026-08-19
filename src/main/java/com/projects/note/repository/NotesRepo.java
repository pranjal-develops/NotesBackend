package com.projects.note.repository;

import com.projects.note.entity.Note;
import com.projects.note.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotesRepo extends CrudRepository<Note, Long> {

    //    List<Note> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String keyword1, String keyword2);
    //Same Thing

    @Query("SELECT n FROM Note n LEFT JOIN n.collaboration c WHERE n.owner = :user OR KEY(c) = :user")
    List<Note> findByUser(@Param("user") User user);

    List<Note> findByOwnerAndTagsContaining(User owner, String tag);

    List<Note> findByOwnerAndTitleContainingOrOwnerAndDescriptionContaining(User owner, String titleQuery, User owner2, String descQuery);

    @Query("SELECT n FROM Note n LEFT JOIN n.collaboration c WHERE (n.owner = :user OR KEY(c) = :user) AND (LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(n.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Note> searchByUser(@Param("user") User user, @Param("keyword") String keyword);
        
    List<Note> findByOwnerAndIsDrawingTrue(User owner);

    List<Note> findByOwnerAndIsDrawingAndTagsContaining(User owner, boolean isDrawing, String tag);

    @Query("Select n from Note n WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(n.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Note> findByKeyword(String keyword);

    List<Note> findByTagsContaining(String tag);

    List<Note> findByIsDrawingTrue();

    List<Note> findByIsDrawingAndTagsContaining(boolean isDrawing, String tag);

}
