package com.app.taskNoteStudio.mappers;

import com.app.taskNoteStudio.domain.dto.TaskDto;
import com.app.taskNoteStudio.domain.entities.Task;
import lombok.Builder;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}
