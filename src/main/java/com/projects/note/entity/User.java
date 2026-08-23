package com.projects.note.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projects.note.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@JsonIgnoreProperties({"notes", "notebooks", "password"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    //    @Lob
//    @Column(length = 10_000_000)
//    private String pfp;
    @Column(columnDefinition = "TEXT")
    private String pfp;
    @Enumerated
    private Role role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Note> notes;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notebook> notebooks;
}
