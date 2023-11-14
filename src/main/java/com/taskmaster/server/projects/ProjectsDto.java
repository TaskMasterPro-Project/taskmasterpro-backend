package com.taskmaster.server.projects;

import lombok.Getter;

@Getter
public record ProjectsDto(Long id, String name) {
    public ProjectsDto {
        // Validation logic can be added if needed
    }

}
