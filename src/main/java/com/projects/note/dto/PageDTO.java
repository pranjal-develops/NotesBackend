package com.projects.note.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {
    private Long id;
    private String title;
    private String content;
    private int pageOrder;
    private OffsetDateTime createdDate;
    private OffsetDateTime updatedDate;
    private Long notebook_id;
}
