package com.app.taskNoteStudio.controller;

import com.app.taskNoteStudio.controllers.TasksController;
import com.app.taskNoteStudio.domain.dto.TaskDto;
import com.app.taskNoteStudio.domain.entities.Task;
import com.app.taskNoteStudio.mappers.TaskMapper;
import com.app.taskNoteStudio.services.TaskService;
import org.apache.logging.log4j.message.ReusableMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tools.jackson.databind.ObjectMapper;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TasksController.class)
public class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private TaskMapper taskMapper;

    @Test
    public void TaskController_ListTasks_ReturnJSON() throws Exception {
        UUID taskListId = UUID.randomUUID();
        Task task  = Task.builder().title("Aufgabe 1").build();
        TaskDto taskDto = TaskDto.builder().title("Aufgabe 1 DTO").build();

        when(taskService.listTasks(taskListId)).thenReturn(List.of(task));
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        ResultActions response = mockMvc.perform(get("/task-lists/{task_list_id}/tasks", taskListId));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Aufgabe 1 DTO"));

    }

    public void TaskController_CreateTask_ReturnCreatedTaskDto() throws Exception {
        UUID taskListId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        TaskDto requestDto = TaskDto.builder().title("Aufgabe 1").build();
        TaskDto responseDto = TaskDto.builder().title("Aufgabe 1").build();
        Task mappedTask = Task.builder().title("Aufgabe 1").build();
        Task createdTask = Task.builder().id(taskId).title("Aufgabe 1").build();

        when(taskService.createTask(eq(taskListId), any(Task.class))).thenReturn(createdTask);
        when(taskMapper.fromDto(any(TaskDto.class))).thenReturn(mappedTask);
        when(taskMapper.toDto(createdTask)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(post("/task-lists/{task_list_id}/tasks", taskListId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(requestDto))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Aufgabe 1"));
    }

    public void TaskController_GetTask_ReturnFoundTaskDto() throws Exception {
        UUID taskListId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        TaskDto responseDto = TaskDto.builder().title("Aufgabe 1").build();
        Task task = Task.builder().title("Aufgabe 1").build();

        when(taskService.getTask(taskListId, taskId)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/task-lists/{task_list_id}/tasks/{task_id}", taskListId, taskId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(responseDto))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Aufgabe 1"));
    }

    public void TaskController_UpdateTask_ReturnUpdatedTaskDto() throws Exception {
        UUID taskListId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        TaskDto requestDto = TaskDto.builder().title("Geändert").build();
        TaskDto responseDto = TaskDto.builder().title("Geändert").build();
        Task updatedTask = Task.builder().title("Geändert").build();
        Task mappedTask = Task.builder().title("Geändert").build();

        when(taskMapper.toDto(updatedTask)).thenReturn(responseDto);
        when(taskMapper.fromDto(any(TaskDto.class))).thenReturn(mappedTask);
        when(taskService.updateTask(eq(taskListId), eq(taskId), any(Task.class))).thenReturn(updatedTask);

        ResultActions response = mockMvc.perform(put("/task-lists/{task_list_id}/tasks/{task_id}", taskListId, taskId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(requestDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Aufgabe 1"));
    }

    public void TaskController_DeleteTask_ReturnOk() throws Exception {
        UUID taskListId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();

        ResultActions response =  mockMvc.perform(delete("/task-lists/{task_list_id}/tasks/{task_id}", taskListId, taskId));

        response.andExpect(status().isOk());
    }
}
