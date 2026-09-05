package com.app.taskNoteStudio.mappers.impl;

import com.app.taskNoteStudio.domain.dto.TaskDto;
import com.app.taskNoteStudio.domain.entities.Task;
import com.app.taskNoteStudio.mappers.TaskMapper;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task fromDto(TaskDto taskDto) {
        return new Task(
                taskDto.id(),
                taskDto.title(),
                taskDto.description(),
                taskDto.dueDate(),
                taskDto.status(),
                taskDto.priority(),
                null,
                null,
                null
        );
    }

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus()
        );
    }
}

