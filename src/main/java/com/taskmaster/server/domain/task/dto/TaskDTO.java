package com.taskmaster.server.domain.task.dto;

import com.taskmaster.server.auth.model.UserModel;
import lombok.*;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private int id;
    private String title;
    private String description;
    private List<String> assignees;
    private LocalDate dueDate;
    private String taskOwner;
}
