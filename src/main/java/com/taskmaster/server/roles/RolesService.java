package com.taskmaster.server.roles;

import com.taskmaster.server.exception.RoleAlreadyExistsException;
import com.taskmaster.server.exception.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolesService {
    private final RolesRepository rolesRepository;
    private final ModelMapper modelMapper;

    public RolesModel convertDtoToEntity(RolesDto dto, String name) {
        RolesModel role = modelMapper.map(dto, RolesModel.class);
        role.setName(name);
        return role;
    }

    public List<RolesModel> getAllRoles() {
        return rolesRepository.findAll();
    }

    public RolesModel createRole(RolesDto dto) {
        // Check if a role with the given ID already exists
        if (dto.name() != null && rolesRepository.findByName(dto.name()).isPresent()) {
            throw new RoleAlreadyExistsException(HttpStatus.BAD_REQUEST,"Role with name '" + dto.name() + "' already exists");
        }

        // If a role with the given ID doesn't exist, proceed with creating the role
        RolesModel role = convertDtoToEntity(dto, dto.name());
        return rolesRepository.saveAndFlush(role);
    }

    public void editRole(Long roleId, RolesDto updatedDto) {
        Optional<RolesModel> optionalRole = rolesRepository.findById(roleId);

        if (!optionalRole.isPresent()) {
            throw new RoleNotFoundException(HttpStatus.NOT_FOUND,"Role not found with ID: " + roleId);
        }

        // Update the fields of the existing role with the values from the updatedDto
        RolesModel existingRole = optionalRole.get();
        existingRole.setName(updatedDto.name());
        // You may need to update other fields as well depending on your project structure

        // Save the updated role
        rolesRepository.save(existingRole);

    }

    public void deleteRoleById(Long roleId) {
        rolesRepository.deleteById(roleId);
    }

}
