package com.app.taskNoteStudio.domain.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
