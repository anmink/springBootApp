package com.app.taskNoteStudio.mappers;

import com.app.taskNoteStudio.domain.dto.TaskListDto;
import com.app.taskNoteStudio.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto (TaskList taskList);
}
