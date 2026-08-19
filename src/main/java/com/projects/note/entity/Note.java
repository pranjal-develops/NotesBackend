package com.projects.note.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projects.note.enums.ShareRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isPublic = false;


    @ElementCollection
    @CollectionTable(name = "note_collaborations", joinColumns =
    @JoinColumn(name = "note_id"))
    @MapKeyJoinColumn(name = "user_id")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Map<User, ShareRole> collaboration = new HashMap<>();

    public Note() {
        this.title = "";
        this.description = "";
        this.createdDate = OffsetDateTime.now();
        this.updatedDate = OffsetDateTime.now();
    }

    public Note(String title1, String description1, String drawingData, String color, boolean isPinned, List<String> tags, boolean isDrawing, User owner) {
        this.title = title1;
        this.description = description1;
        this.createdDate = OffsetDateTime.now();
        this.updatedDate = OffsetDateTime.now();
        this.drawingData = drawingData;
        this.color = color;
        this.isPinned = isPinned;
        this.isDrawing = isDrawing;
        this.tags = tags;
        this.owner = owner;
    }
}
