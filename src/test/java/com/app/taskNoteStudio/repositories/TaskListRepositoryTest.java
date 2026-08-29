package com.app.taskNoteStudio.repositories;

import com.app.taskNoteStudio.domain.entities.TaskList;
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
public class TaskListRepositoryTest {

    @Autowired
    private TaskListRepository taskListRepository;

    @Test
    public void TaskListRepository_SaveTaskList_ReturnTaskList() {
        TaskList taskList = TaskList.builder()
                .title("test tasklist")
                .description("iujbgwbg")
                .created(LocalDateTime.parse("2026-08-24T00:00:00"))
                .updated(LocalDateTime.parse("2026-08-24T00:00:00"))
                .build();
        TaskList savedTaskList = taskListRepository.save(taskList);

        Assertions.assertThat(savedTaskList.getId()).isNotNull();
        Assertions.assertThat(savedTaskList.getId()).isNotNull();
    }

    @Test
    public void TaskListRepository_UpdateTaskList_ReturnUpdatedTaskList() {
        TaskList taskList = TaskList.builder()
                .title("Test Tasklist")
                .description("iujbgwbg")
                .created(LocalDateTime.parse("2026-08-24T00:00:00"))
                .updated(LocalDateTime.parse("2026-08-24T00:00:00"))
                .build();
        TaskList savedTaskList = taskListRepository.save(taskList);

        savedTaskList.setTitle("Test Tasklist (geändert)");
        TaskList updatedTaskList = taskListRepository.save(savedTaskList);

        Assertions.assertThat(updatedTaskList).isNotNull();
        Assertions.assertThat(updatedTaskList.getId()).isEqualTo(savedTaskList.getId());
        Assertions.assertThat(updatedTaskList.getTitle()).isEqualTo("Test Tasklist (geändert)");
    }

    @Test
    public void TaskListRepository_FindAllTaskLists_ReturnAllTaskLists() {
        TaskList taskList1 = TaskList.builder()
                .title("Test Tasklist 1")
                .description("iujbgwbg")
                .created(LocalDateTime.parse("2026-08-24T00:00:00"))
                .updated(LocalDateTime.parse("2026-08-24T00:00:00"))
                .build();
        taskListRepository.save(taskList1);

        TaskList taskList2 = TaskList.builder()
                .title("Test Tasklist 2")
                .description("iujbgwbg")
                .created(LocalDateTime.parse("2026-08-24T00:00:00"))
                .updated(LocalDateTime.parse("2026-08-24T00:00:00"))
                .build();
        taskListRepository.save(taskList2);

        List<TaskList> taskList = taskListRepository.findAll();

        Assertions.assertThat(taskList).isNotNull();
        Assertions.assertThat(taskList.size()).isEqualTo(2);
    }

    @Test
    public void TaskListRepository_FindTaskListById_ReturnTaskListById() {
        TaskList taskList = TaskList.builder()
                .title("Test Tasklist 1")
                .description("iujbgwbg")
                .created(LocalDateTime.parse("2026-08-24T00:00:00"))
                .updated(LocalDateTime.parse("2026-08-24T00:00:00"))
                .build();
        TaskList savedTaskList = taskListRepository.save(taskList);

        Optional<TaskList> foundTaskList = taskListRepository.findById(savedTaskList.getId());

        Assertions.assertThat(foundTaskList).isPresent();
        Assertions.assertThat(foundTaskList.get().getTitle()).isEqualTo("Test Tasklist 1");
    }

    @Test
    public void TaskListRepository_DeleteTaskList_Return_DeletedTaskList() {
        TaskList taskList = TaskList.builder()
                .title("Test Tasklist 1")
                .description("iujbgwbg")
                .created(LocalDateTime.parse("2026-08-24T00:00:00"))
                .updated(LocalDateTime.parse("2026-08-24T00:00:00"))
                .build();
        TaskList savedTaskList = taskListRepository.save(taskList);

        taskListRepository.deleteById(savedTaskList.getId());

        Optional<TaskList> deletedTaskList = taskListRepository.findById(savedTaskList.getId());

        Assertions.assertThat(deletedTaskList).isNotPresent();
    }
}
