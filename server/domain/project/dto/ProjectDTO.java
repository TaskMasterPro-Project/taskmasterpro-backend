package com.taskmaster.server.domain.project.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private long id;
    private String name;
    private String description;
}
