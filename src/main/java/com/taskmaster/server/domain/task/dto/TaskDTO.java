package com.taskmaster.server.domain.task.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private int id;
    private String title;
    private String description;
}
