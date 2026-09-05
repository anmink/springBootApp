package com.app.taskNoteStudio.domain.dto;

import com.app.taskNoteStudio.domain.entities.TaskPriority;
import com.app.taskNoteStudio.domain.entities.TaskStatus;
import lombok.Builder;
import org.springframework.web.ErrorResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {
}
