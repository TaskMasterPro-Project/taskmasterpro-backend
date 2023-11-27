package com.taskmaster.server.roles;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RolesMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public RolesMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
