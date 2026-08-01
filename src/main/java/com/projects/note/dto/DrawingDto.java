package com.projects.note.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawingDto {
    private Long id;
    private OffsetDateTime createdDate;
    private OffsetDateTime updatedDate;
    private String drawingData;
    private String color;
    private boolean isPinned;
    private List<String> tags;
}
