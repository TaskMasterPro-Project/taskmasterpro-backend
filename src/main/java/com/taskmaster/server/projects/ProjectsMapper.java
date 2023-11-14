package com.taskmaster.server.projects;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectsMapper {

    @Mapping(target = "name", source = "dto.name")
    ProjectsModel convertDtoToEntity(ProjectsDto dto, String name);

    // If you need to map the name from another source, you can add a separate method like this:
    // @Mapping(target = "name", source = "otherSource")
    // ProjectsModel convertDtoToEntityWithOtherSource(ProjectsDto dto, String otherSource);

}
