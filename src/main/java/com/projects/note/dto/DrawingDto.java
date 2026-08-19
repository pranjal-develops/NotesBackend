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
public class DrawingDto {
    private Long id;
    private OffsetDateTime createdDate;
    private OffsetDateTime updatedDate;
    private String drawingData;
    private String color;
    private boolean isPinned;
    private List<String> tags;
    private User owner;
    private Map<Long, ShareRole> collaboration = new HashMap<>();
}
