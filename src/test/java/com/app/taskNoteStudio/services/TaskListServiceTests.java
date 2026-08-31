package com.app.taskNoteStudio.services;

import com.app.taskNoteStudio.domain.entities.TaskList;
import com.app.taskNoteStudio.repositories.TaskListRepository;
import com.app.taskNoteStudio.repositories.TaskRepository;
import com.app.taskNoteStudio.services.impl.TaskListServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskListServiceTests {

    @Mock
    private TaskListRepository taskListRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskListServiceImpl taskListService;

    @Test
    public void TaskListService_CreateTaskList_ReturnSavedTaskList() {
        TaskList taskList = TaskList.builder()
                .title("TaskList")
                .build();

        when(taskListRepository.save(any(TaskList.class))).thenReturn(taskList);

        TaskList result = taskListService.createTaskList(taskList);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getTitle()).isEqualTo("TaskList");
        verify(taskListRepository, times(1)).save(any(TaskList.class));
    }

    @Test
    public void TaskListService_ListTaskLists_ReturnAllTaskLists() {
        TaskList taskList1 = TaskList.builder()
                .title("TaskList 1")
                .build();

        TaskList taskList2 = TaskList.builder()
                .title("TaskList 2")
                .build();

        when(taskListRepository.findAll()).thenReturn(List.of(taskList1, taskList2));

        List<TaskList> result = taskListService.listTaskLists();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        verify(taskListRepository, times(1)).findAll();
    }


    @Test
    public void TaskListService_GetTaskList_ReturnTaskList() {
        UUID taskListId = UUID.randomUUID();

        TaskList taskList = TaskList.builder()
                .title("TaskList")
                .build();

        when(taskListRepository.findById(taskListId)).thenReturn(Optional.of(taskList));

        Optional<TaskList> result = taskListService.getTaskList(taskListId);

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getTitle()).isEqualTo("TaskList");
        verify(taskListRepository, times(1)).findById(taskListId);
    }

    @Test
    public void TaskListService_UpdateTaskList_ReturnUpdatedTaskList() {
        UUID taskListId = UUID.randomUUID();

        TaskList taskList = TaskList.builder()
                .id(taskListId)
                .title("TaskList")
                .build();

        when(taskListRepository.save(any(TaskList.class))).thenReturn(taskList);
        when(taskListRepository.findById(taskListId)).thenReturn(Optional.of(taskList));

        TaskList result = taskListService.updateTaskList(taskListId, taskList);

        Assertions.assertThat(result).isNotNull();
        verify(taskListRepository, times(1)).save(any(TaskList.class));
    }

    @Test
    public void TaskListService_DeleteTaskList_ReturnDeletedTaskList() {
        UUID taskListId = UUID.randomUUID();

        taskListService.deleteTaskList(taskListId);

        verify(taskListRepository, times(1)).deleteById(taskListId);
    }
}
