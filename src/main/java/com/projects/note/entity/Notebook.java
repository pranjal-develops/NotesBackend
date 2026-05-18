package com.projects.note.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Notebook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 1000)
    private String description;
    private String color;
    private OffsetDateTime createdDate;
    private OffsetDateTime updatedDate;

    @OneToMany(mappedBy = "notebook", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("pageOrder ASC")
    private List<Page> pages = new ArrayList<>();

    public Notebook() {
        this.createdDate = OffsetDateTime.now();
        this.updatedDate = OffsetDateTime.now();
    }

}
