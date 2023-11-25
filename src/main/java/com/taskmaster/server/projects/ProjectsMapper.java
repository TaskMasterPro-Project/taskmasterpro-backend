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


}