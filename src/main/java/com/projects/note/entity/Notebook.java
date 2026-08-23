package com.projects.note.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projects.note.enums.ShareRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Lob
    @Column(length = 10_000_000)
    private String logo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isPublic = false;


    @ElementCollection
    @CollectionTable(name = "notebook_collaborations", joinColumns =
    @JoinColumn(name = "notebook_id"))
    @MapKeyJoinColumn(name = "user_id")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Map<User, ShareRole> collaboration = new HashMap<>();


    @OneToMany(mappedBy = "notebook", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("pageOrder ASC")
    private List<Page> pages = new ArrayList<>();

    public Notebook() {
        this.name = "";
        this.createdDate = OffsetDateTime.now();
        this.updatedDate = OffsetDateTime.now();
    }

}
