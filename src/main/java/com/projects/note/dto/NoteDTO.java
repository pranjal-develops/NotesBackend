package com.projects.note.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

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
    private String color;
    private boolean isPinned;
    private List<String> tags;
    private boolean isDrawing;

    public NoteDTO(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdDate = OffsetDateTime.now();
        this.updatedDate = OffsetDateTime.now();
    }
}
