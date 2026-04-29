package com.projects.note.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data //This annotation can create getter and setter
@AllArgsConstructor //This annotation will create a constructor with parameters with all fields
@NoArgsConstructor //This annotation will create a constructor with no parameter 
public class NoteDTO {
    private Long id;
    private String title;
    private String description;
    private OffsetDateTime createdDate;
    private OffsetDateTime updatedDate;
    private String drawingData;


    // Constructors
//    public NoteDTO() {}
//
//    public NoteDTO(Long id, String title, String description) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//    }
//

    public NoteDTO(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdDate = OffsetDateTime.now();
        this.updatedDate = OffsetDateTime.now();
    }

    public NoteDTO(Long id, String title, String description, OffsetDateTime createdDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = OffsetDateTime.now();

    }

    public NoteDTO(Long id, String title, String description, OffsetDateTime createdDate, String drawingData) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = OffsetDateTime.now();
        this.drawingData = drawingData;
    }
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }


}
