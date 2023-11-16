package com.taskmaster.server.projects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectsService {
    private final ProjectsRepository projectsRepository;
    private final ProjectsMapper projectsMapper;

    public Optional<ProjectsModel> findProjectById(Long projectId) {
        return projectsRepository.findById(projectId);
    }

    public List<ProjectsModel> getAllProjects() {
        return projectsRepository.findAll();
    }

    public ProjectsModel createProject(ProjectsDto dto) {
        // Check if a project with the given ID already exists
        if (dto.id() != null && projectsRepository.findById(dto.id()).isPresent()) {
            throw new ProjectAlreadyExistsException("Project with ID '" + dto.id() + "' already exists");
        }

        // If a project with the given ID doesn't exist, proceed with creating the project
        ProjectsModel project = projectsMapper.convertDtoToEntity(dto, dto.name());
        return projectsRepository.saveAndFlush(project);
    }

    public void editProject(Long projectId, ProjectsDto updatedDto) {
        Optional<ProjectsModel> optionalProject = projectsRepository.findById(projectId);

        if (!optionalProject.isPresent()) {
            throw new ProjectNotFoundException("Project not found with ID: " + projectId);
        }

        // Update the fields of the existing project with the values from the updatedDto
        ProjectsModel existingProject = optionalProject.get();
        existingProject.setName(updatedDto.name());
        // You may need to update other fields as well depending on your project structure

        // Save the updated project
        projectsRepository.save(existingProject);

    }

    public void deleteProjectById(Long projectId) {
        projectsRepository.deleteById(projectId);
    }
}
