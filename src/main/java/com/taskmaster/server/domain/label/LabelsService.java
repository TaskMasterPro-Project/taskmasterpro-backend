package com.taskmaster.server.domain.label;

import com.taskmaster.server.domain.label.dto.CreateEditLabelRequest;
import com.taskmaster.server.domain.label.dto.LabelDTO;
import com.taskmaster.server.domain.label.model.LabelModel;
import com.taskmaster.server.domain.project.ProjectsRepository;
import com.taskmaster.server.domain.task.TasksRepository;
import com.taskmaster.server.exception.LabelAlreadyExistsException;
import com.taskmaster.server.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LabelsService
{
    private final LabelsRepository labelsRepository;
    private final ProjectsRepository projectsRepository;

    public LabelsService(
            LabelsRepository labelsRepository,
            ProjectsRepository projectsRepository)
    {
        this.labelsRepository = labelsRepository;
        this.projectsRepository = projectsRepository;
    }

    @Transactional
    public void createLabel(CreateEditLabelRequest dto, long projectId)
    {
        if (dto.name() != null && labelsRepository.existsByNameAndProjectId(dto.name(), projectId))
        {
            throw new LabelAlreadyExistsException(HttpStatus.BAD_REQUEST,
                    "Label with name '" + dto.name() + "' already exists");
        }

        var project = projectsRepository.findById(projectId)
                .orElseThrow( () -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Project not found"));

        var model = LabelModel.builder()
                .name(dto.name())
                .project(project)
                .build();

        labelsRepository.save(model);
    }

    @Transactional
    public void deleteLabelById(long labelId)
    {
        labelsRepository.deleteById(labelId);

    }

    public List<LabelDTO> getLabelsForProject(Long projectId) {
        return labelsRepository
                .findAllByProjectId(projectId)
                .stream()
                .map(labelModel -> new LabelDTO(
                    labelModel.getId(),
                    labelModel.getProject().getId(),
                    labelModel.getName())
                ).toList();
    }
}
