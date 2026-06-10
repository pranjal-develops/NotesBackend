package com.projects.note.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String contentHtml;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb") // Use jsonb for PostgreSQL or json for MySQL
    private List<String> drawings;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Map<String, Object>> charts;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Map<String, String>> codeBlocks;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> images;
    //    private int pageOrder; // Commented it since primitive int cannot be null, this giving error in DTO when pageOrder is not provided
    private Integer pageOrder;
    private OffsetDateTime createdDate;
    private OffsetDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notebook_id")
    private Notebook notebook;

    public Page() {
        this.createdDate = OffsetDateTime.now();
        this.updatedDate = OffsetDateTime.now();
    }

}
