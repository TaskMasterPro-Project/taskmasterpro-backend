package com.taskmaster.server.projects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectsService implements IProjectsService {
    private final ProjectsRepository projectsRepository;
    private final ProjectsMapper projectsMapper;

    @Override
    public Optional<ProjectsModel> findProjectById(Long projectId) {
        return projectsRepository.findById(projectId);
    }

    @Override
    public List<ProjectsModel> getAllProjects() {
        return projectsRepository.findAll();
    }

    @Override
    public ProjectsModel createProject(ProjectsDto dto) {
        // Check if a project with the given name already exists
        Optional<ProjectsModel> existingProjectByName = projectsRepository.findProjectByName(dto.getName());
        if (existingProjectByName.isPresent()) {
            throw new ProjectAlreadyExistsException("Project with name '" + dto.getName() + "' already exists");
        }

        // Check if a project with the given ID already exists
        if (dto.getId() != null && projectsRepository.findById(dto.getId()).isPresent()) {
            throw new ProjectAlreadyExistsException("Project with ID '" + dto.getId() + "' already exists");
        }

        // If neither a project with the given name nor ID exists, proceed with creating the project
        ProjectsModel project = projectsMapper.convertDtoToEntity(dto, dto.getName());
        return projectsRepository.saveAndFlush(project);
    }




    @Override
    public void editProject(Long projectId, ProjectsDto updatedDto) {
        Optional<ProjectsModel> optionalProject = projectsRepository.findById(projectId);

        if (optionalProject.isPresent()) {
            ProjectsModel existingProject = optionalProject.get();
            // Update the fields of the existing project with the values from the updatedDto
            existingProject.setName(updatedDto.getName());
            // You may need to update other fields as well depending on your project structure

            // Save the updated project
            projectsRepository.save(existingProject);
        } else {
            // Handle the case where the project with the given ID is not found
            // You can throw an exception or handle it based on your specific requirements
            throw new ProjectNotFoundException("Project not found with ID: " + projectId);
        }
    }

    @Override
    public void deleteProjectById(Long projectId) {
        projectsRepository.deleteById(projectId);
    }
}
