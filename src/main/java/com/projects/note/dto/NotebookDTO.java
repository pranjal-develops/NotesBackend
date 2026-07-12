package com.projects.note.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotebookDTO {
    private Long id;
    private String name;
    private String description;
    private String color;
    private OffsetDateTime createdDate;
    private OffsetDateTime updatedDate;
    private String logo;
    private List<PageSummaryDTO> pages;
}
