package com.projects.note.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 1000)
    private String description;
    private OffsetDateTime createdDate;
    private OffsetDateTime updatedDate;
    @ElementCollection
    private List<String> tags;
    private String color;
    private boolean isPinned;
    private boolean isDrawing;

    @Column(columnDefinition = "TEXT")
    private String drawingData;

    public Note() {
        this.title = "";
        this.description = "";
        this.createdDate = OffsetDateTime.now();
        this.updatedDate = OffsetDateTime.now();
    }

//    public Note(String title1, String description1) {
//        this.title = title1;
//        this.description = description1;
//        this.createdDate = OffsetDateTime.now();
//        this.updatedDate = OffsetDateTime.now();
//    }

//    public Note(Long id, String title1, String description1) {
//        this.id = id;
//        this.title = title1;
//        this.description = description1;
//        this.createdDate = OffsetDateTime.now();
//        this.updatedDate = OffsetDateTime.now();
//    }

//    public Note(Long id, String title1, String description1, String drawingData) {
//        this.id = id;
//        this.title = title1;
//        this.description = description1;
//        this.createdDate = OffsetDateTime.now();
//        this.updatedDate = OffsetDateTime.now();
//        this.drawingData = drawingData;
//    }

//    public Note(String title1, String description1, String drawingData, String color, boolean isPinned) {
//        this.title = title1;
//        this.description = description1;
//        this.createdDate = OffsetDateTime.now();
//        this.updatedDate = OffsetDateTime.now();
//        this.drawingData = drawingData;
//        this.color = color;
//        this.isPinned = isPinned;
//    }

    public Note(String title1, String description1, String drawingData, String color, boolean isPinned, List<String> tags) {
        this.title = title1;
        this.description = description1;
        this.createdDate = OffsetDateTime.now();
        this.updatedDate = OffsetDateTime.now();
        this.drawingData = drawingData;
        this.color = color;
        this.isPinned = isPinned;
        this.tags = tags;
    }

    public Note(String title1, String description1, String drawingData, String color, boolean isPinned, List<String> tags, boolean isDrawing) {
        this.title = title1;
        this.description = description1;
        this.createdDate = OffsetDateTime.now();
        this.updatedDate = OffsetDateTime.now();
        this.drawingData = drawingData;
        this.color = color;
        this.isPinned = isPinned;
        this.isDrawing = isDrawing;
        this.tags = tags;
    }


//    public Note(Long id, String title1, String description1, String drawingData, String color, boolean isPinned) {
//        this.id = id;
//        this.title = title1;
//        this.description = description1;
//        this.createdDate = OffsetDateTime.now();
//        this.updatedDate = OffsetDateTime.now();
//        this.drawingData = drawingData;
//        this.color = color;
//        this.isPinned = isPinned;
//    }
}
