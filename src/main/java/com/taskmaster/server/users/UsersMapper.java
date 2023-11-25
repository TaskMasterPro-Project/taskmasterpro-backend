package com.taskmaster.server.users;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public UsersMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
