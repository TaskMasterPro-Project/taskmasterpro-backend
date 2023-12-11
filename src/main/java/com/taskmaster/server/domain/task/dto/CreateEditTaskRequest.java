package com.taskmaster.server.domain.task.dto;

import com.taskmaster.server.auth.model.UserModel;

import java.time.LocalDate;
import java.util.List;

public record CreateEditTaskRequest(String title, String description, List<String> assignees, LocalDate dueDate) {
}
