package com.taskmaster.server.projects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectsMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public ProjectsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProjectsModel convertDtoToEntity(ProjectsDto dto, String name) {
        ProjectsModel project = modelMapper.map(dto, ProjectsModel.class);
        project.setName(name);
        return project;
    }
}