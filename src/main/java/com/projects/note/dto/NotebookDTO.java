package com.projects.note.dto;

import com.projects.note.entity.User;
import com.projects.note.enums.ShareRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private User owner;
    private Map<Long, ShareRole> collaboration = new HashMap<>();
}
