package com.app.taskNoteStudio.repositories;

import com.app.taskNoteStudio.domain.entities.Task;
import com.app.taskNoteStudio.domain.entities.TaskList;
import com.app.taskNoteStudio.domain.entities.TaskPriority;
import com.app.taskNoteStudio.domain.entities.TaskStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskListRepository taskListRepository;

    @Test
    public void TaskRepository_SaveAll_ReturnSavedTask() {
        // Arrange
        Task task = Task.builder()
                .title("aufgabe 1")
                .description("ngrongwong")
                .dueDate(LocalDateTime.parse("2027-03-12T00:00:00"))
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .created(LocalDateTime.parse("2026-08-24T00:00:00"))
                .updated(LocalDateTime.parse("2026-08-24T00:00:00"))
                .build();

        // Act
        Task savedTask = taskRepository.save(task);

        // Assert
        Assertions.assertThat(savedTask).isNotNull();
        Assertions.assertThat(savedTask.getId()).isNotNull();
    }

    @Test
    public void TaskRepository_findAll_ReturnAllTasks() {
        // Arrange
        Task task1 = Task.builder()
                .title("aufgabe 1")
                .description("ngrongwong")
                .dueDate(LocalDateTime.parse("2027-03-13T00:00:00"))
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .created(LocalDateTime.parse("2026-08-24T00:00:00"))
                .updated(LocalDateTime.parse("2026-08-24T00:00:00"))
                .build();
        taskRepository.save(task1);

        Task task2 = Task.builder()
                .title("aufgabe 2")
                .description("ngrongwong")
                .dueDate(LocalDateTime.parse("2027-03-14T00:00:00"))
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .created(LocalDateTime.parse("2026-08-24T00:00:00"))
                .updated(LocalDateTime.parse("2026-08-24T00:00:00"))
                .build();
        taskRepository.save(task2);
        List<Task> taskList = taskRepository.findAll();

        // Assert
        Assertions.assertThat(taskList).isNotNull();
    }

    @Test
    public void TaskRepository_findByTaskListId_ReturnAllFoundTasks() {
        // Arrange
        Task task = Task.builder()
                .title("aufgabe 1")
                .description("ngrongwong")
                .dueDate(LocalDateTime.parse("2027-03-13T00:00:00"))
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .created(LocalDateTime.parse("2026-08-24T00:00:00"))
                .updated(LocalDateTime.parse("2026-08-24T00:00:00"))
                .build();

        //Act
        Task savedTask = taskRepository.save(task);
        Optional<Task> taskList = taskRepository.findById(savedTask.getId());

        // Assert
        Assertions.assertThat(taskList).isNotNull();
    }

    @Test
    public void TaskRepository_findByTaskListIdAndId_ReturnFoundTask() {
        TaskList taskList = TaskList.builder()
                .title("test tasklist")
                .description("iujbgwbg")
                .created(LocalDateTime.parse("2026-08-24T00:00:00"))
                .updated(LocalDateTime.parse("2026-08-24T00:00:00"))
                .build();
        taskListRepository.save(taskList);

        Task task = Task.builder()
                .title("aufgabe 1")
                .description("ngrongwong")
                .dueDate(LocalDateTime.parse("2027-03-13T00:00:00"))
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .taskList(taskList)
                .created(LocalDateTime.parse("2026-08-24T00:00:00"))
                .updated(LocalDateTime.parse("2026-08-24T00:00:00"))
                .build();
        taskRepository.save(task);

        Optional<Task> listTaskByTaskListId = taskRepository.findByTaskListIdAndId(taskList.getId(), task.getId());

        Assertions.assertThat(listTaskByTaskListId).isNotNull();
        Assertions.assertThat(listTaskByTaskListId).isPresent();
        Assertions.assertThat(listTaskByTaskListId.get().getTitle()).isEqualTo("aufgabe 1");
    }

    @Test
    public void TaskRepository_deleteByTaskListIdAndId_ReturnDeletedTask() {
        TaskList taskList = TaskList.builder()
                .title("test tasklist")
                .description("iujbgwbg")
                .created(LocalDateTime.parse("2026-08-24T00:00:00"))
                .updated(LocalDateTime.parse("2026-08-24T00:00:00"))
                .build();
        taskListRepository.save(taskList);

        Task task = Task.builder()
                .title("aufgabe 1")
                .description("ngrongwong")
                .dueDate(LocalDateTime.parse("2027-03-13T00:00:00"))
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .taskList(taskList)
                .created(LocalDateTime.parse("2026-08-24T00:00:00"))
                .updated(LocalDateTime.parse("2026-08-24T00:00:00"))
                .build();
        taskRepository.save(task);

        taskRepository.deleteByTaskListIdAndId(taskList.getId(), task.getId());

        Optional<Task> deletedTask = taskRepository.findByTaskListIdAndId(taskList.getId(), task.getId());

        Assertions.assertThat(deletedTask).isNotPresent();
    }

    @Test
    public void TaskRepository_updateTaskById_ReturnUpdatedTask() {
        Task task = Task.builder()
                .title("aufgabe 1")
                .description("ngrongwong")
                .dueDate(LocalDateTime.parse("2027-03-13T00:00:00"))
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .created(LocalDateTime.parse("2026-08-24T00:00:00"))
                .updated(LocalDateTime.parse("2026-08-24T00:00:00"))
                .build();
        Task savedTask = taskRepository.save(task);

        savedTask.setTitle("Aufgabe 1 (geändert)");
        savedTask.setStatus(TaskStatus.CLOSED);
        Task updatedTask = taskRepository.save(savedTask);

        Assertions.assertThat(updatedTask).isNotNull();
        Assertions.assertThat(updatedTask.getId()).isEqualTo(savedTask.getId());
        Assertions.assertThat(updatedTask.getTitle()).isEqualTo("Aufgabe 1 (geändert)");
        Assertions.assertThat(updatedTask.getStatus()).isEqualTo(TaskStatus.CLOSED);
    }
}
