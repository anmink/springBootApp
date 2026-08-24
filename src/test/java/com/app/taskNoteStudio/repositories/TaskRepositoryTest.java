package com.app.taskNoteStudio.repositories;

import com.app.taskNoteStudio.domain.entities.Task;
import com.app.taskNoteStudio.domain.entities.TaskPriority;
import com.app.taskNoteStudio.domain.entities.TaskStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

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
}
