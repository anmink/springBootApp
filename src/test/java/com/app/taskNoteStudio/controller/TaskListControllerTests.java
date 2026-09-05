package com.app.taskNoteStudio.controller;

import com.app.taskNoteStudio.controllers.TaskListController;
import com.app.taskNoteStudio.domain.dto.TaskListDto;
import com.app.taskNoteStudio.domain.entities.TaskList;
import com.app.taskNoteStudio.mappers.TaskListMapper;
import com.app.taskNoteStudio.services.TaskListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskListController.class)
public class TaskListControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskListService taskListService;

    @MockitoBean
    private TaskListMapper taskListMapper;

    @Test
    public void TaskListController_ListTaskLists_ReturnTaskLists() throws Exception {
        UUID taskListId = UUID.randomUUID();
        TaskList taskList = TaskList.builder().title("Task List").build();
        TaskListDto taskListDto = TaskListDto.builder().title("Task List").build();

        when(taskListService.listTaskLists()).thenReturn(List.of(taskList));
        when(taskListMapper.toDto(taskList)).thenReturn(taskListDto);

        ResultActions response = mockMvc.perform(get("/task-lists"));

        response.andExpect(status().isOk());
    }

    @Test
    public void TaskListController_GetTaskList_ReturnTaskList() throws Exception {
        UUID taskListId = UUID.randomUUID();
        TaskList taskList = TaskList.builder().title("Task List").build();
        TaskListDto taskListDto = TaskListDto.builder().title("Task List").build();

        when(taskListService.getTaskList(taskListId)).thenReturn(Optional.of(taskList));
        when(taskListMapper.toDto(taskList)).thenReturn(taskListDto);

        ResultActions response = mockMvc.perform(get("/task-lists/{task_list_id}", taskListId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(taskListDto)));

        response.andExpect(status().isOk());
    }

    @Test
    public void TaskListController_CreateTaskList_ReturnCreatedTaskList() throws Exception {
        UUID taskListId = UUID.randomUUID();
        TaskListDto requestDto = TaskListDto.builder().title("Tasklist").build();
        TaskListDto responseDto = TaskListDto.builder().title("Tasklist").build();
        TaskList mappedTasklist = TaskList.builder().title("Tasklist").build();
        TaskList createdTasklist = TaskList.builder().title("Tasklist").build();

        when(taskListService.createTaskList(any(TaskList.class))).thenReturn(createdTasklist);
        when(taskListMapper.fromDto(requestDto)).thenReturn(mappedTasklist);
        when(taskListMapper.toDto(createdTasklist)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(post("/task-lists")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(requestDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Tasklist"));
    }

    @Test
    public void TaskListController_UpdatedTaskList_ReturnUpdatedTaskList() throws Exception {
        UUID taskListId = UUID.randomUUID();
        TaskListDto requestDto = TaskListDto.builder().title("Tasklist neu").build();
        TaskListDto responseDto = TaskListDto.builder().title("Tasklist neu").build();
        TaskList mappedTasklist = TaskList.builder().title("Tasklist neu").build();
        TaskList updatedTasklist = TaskList.builder().title("Tasklist neu").build();

        when(taskListService.updateTaskList(eq(taskListId), any(TaskList.class))).thenReturn(updatedTasklist);
        when(taskListMapper.fromDto(requestDto)).thenReturn(mappedTasklist);
        when(taskListMapper.toDto(updatedTasklist)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(put("/task-lists/{task_list_id}", taskListId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(requestDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Tasklist neu"));
    }

    @Test
    public void TaskListController_DeleteTaskList_ReturnOK() throws Exception {
        UUID taskListId = UUID.randomUUID();

        ResultActions response = mockMvc.perform(delete("/task-lists/{task_list_id}", taskListId));

        response.andExpect(status().isOk());
    }
}
