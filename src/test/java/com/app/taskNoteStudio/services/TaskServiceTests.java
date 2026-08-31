package com.app.taskNoteStudio.services;

import com.app.taskNoteStudio.domain.entities.Task;
import com.app.taskNoteStudio.domain.entities.TaskList;
import com.app.taskNoteStudio.domain.entities.TaskPriority;
import com.app.taskNoteStudio.domain.entities.TaskStatus;
import com.app.taskNoteStudio.repositories.TaskListRepository;
import com.app.taskNoteStudio.repositories.TaskRepository;
import com.app.taskNoteStudio.services.impl.TaskServiceImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.OptionalAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskListRepository taskListRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void TaskService_CreateTask_ReturnSavedTask() {
        UUID taskListId = UUID.randomUUID();

        TaskList taskList = TaskList.builder()
                .title("TaskList")
                .build();

        Task task = Task.builder()
                .title("Aufgabe 1")
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .build();

        when(taskListRepository.findById(taskListId)).thenReturn(Optional.of(taskList));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.createTask(taskListId, task);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getTitle()).isEqualTo("Aufgabe 1");
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void TaskService_ListTasks_ReturnAllTasks() {
        UUID taskListId = UUID.randomUUID();

        TaskList taskList = TaskList.builder()
                .title("TaskList")
                .build();

        Task task1 = Task.builder()
                .title("Aufgabe 1")
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .build();

        Task task2 = Task.builder()
                .title("Aufgabe 2")
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .build();

        when(taskRepository.findByTaskListId(taskListId)).thenReturn(List.of(task1, task2));

        List<Task> result = taskService.listTasks(taskListId);

        Assertions.assertThat(result).hasSize(2);
        verify(taskRepository, times(1)).findByTaskListId(taskListId);
    }

    @Test
    public void TaskService_getTaskById_ReturnTaskById() {
        UUID taskListId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();

        Task task = Task.builder()
                .title("Aufgabe 1")
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .build();

        when(taskRepository.findByTaskListIdAndId(taskListId, taskId)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTask(taskListId, taskId);

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getTitle()).isEqualTo("Aufgabe 1");
        verify(taskRepository, times(1)).findByTaskListIdAndId(taskListId, taskId);
    }

    @Test
    public void TaskService_updateTask_ReturnUpdatedTask() {
        UUID taskListId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();

        Task task = Task.builder()
                .id(taskId)
                .title("Aufgabe 1")
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .build();

        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskRepository.findByTaskListIdAndId(taskListId, taskId)).thenReturn(Optional.of(task));

        Task result = taskService.updateTask(taskListId, taskId, task);

        Assertions.assertThat(result).isNotNull();
        verify(taskRepository, times(1)).save(any(Task.class));


    }

    @Test
    public void TaskService_deleteTask_ReturnDeletedTask() {
        UUID taskListId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();

        taskService.deleteTask(taskListId, taskId);

        verify(taskRepository, times(1)).deleteByTaskListIdAndId(taskListId, taskId);
    }
}
