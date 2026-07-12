package com.projects.note.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {
    private Long id;
    private String title;
    private String contentHtml;
    private List<String> drawings;
    private List<Map<String, Object>> charts;
    private List<Map<String, String>> codeBlocks;
    private List<String> images;
    private Integer pageOrder;
    private OffsetDateTime createdDate;
    private OffsetDateTime updatedDate;
    private Long notebook_id;
}
