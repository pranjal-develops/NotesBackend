package com.projects.note.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SyncRequestDTO {
    private List<NoteDTO> notes;
    private List<NotebookDTO> notebooks;
}
