package com.app.taskNoteStudio.domain.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record TaskListDto(
        UUID id,
        String title,
        String description,
        Integer count,
        Double progress,
        List<TaskDto> tasks
) {
}
