package com.taskmaster.server.projects;

import com.taskmaster.server.exception.ProjectAlreadyExistsException;
import com.taskmaster.server.exception.ProjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectsService {
    private final ProjectsRepository projectsRepository;
    private final ModelMapper modelMapper;

    public ProjectsModel convertDtoToEntity(ProjectsDto dto, String name) {
        ProjectsModel project = modelMapper.map(dto, ProjectsModel.class);
        project.setName(name);
        return project;
    }

    public List<ProjectsModel> getAllProjects() {
        return projectsRepository.findAll();
    }

    public ProjectsModel createProject(ProjectsDto dto) {
        // Check if a project with the given ID already exists
        if (dto.name() != null && projectsRepository.findByName(dto.name()).isPresent()) {
            throw new ProjectAlreadyExistsException(HttpStatus.BAD_REQUEST,"Project with name '" + dto.name() + "' already exists");
        }

        // If a project with the given ID doesn't exist, proceed with creating the project
        ProjectsModel project = convertDtoToEntity(dto, dto.name());
        return projectsRepository.saveAndFlush(project);
    }

    public void editProject(Long projectId, ProjectsDto updatedDto) {
        Optional<ProjectsModel> optionalProject = projectsRepository.findById(projectId);

        if (!optionalProject.isPresent()) {
            throw new ProjectNotFoundException(HttpStatus.NOT_FOUND,"Project not found with ID: " + projectId);
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
