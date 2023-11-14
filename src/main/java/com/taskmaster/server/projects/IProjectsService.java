package com.taskmaster.server.projects;

import java.util.List;
import java.util.Optional;

public interface IProjectsService {
    List<ProjectsModel> getAllProjects();
    ProjectsModel createProject(ProjectsDto dto);
    Optional<ProjectsModel> findProjectById(Long projectId);
    void editProject(Long projectId, ProjectsDto updatedDto);
    void deleteProjectById(Long projectId);
}
