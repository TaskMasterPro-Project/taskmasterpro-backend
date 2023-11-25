package com.taskmaster.server.users;

import com.taskmaster.server.projects.ProjectsModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    private final ModelMapper modelMapper;

    public UsersModel convertDtoToEntity(UsersDto dto, String username,String password,String email) {
        UsersModel user = modelMapper.map(dto, UsersModel.class);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }

    public List<UsersModel> getAllUsers() {
        return usersRepository.findAll();
    }

    public UsersModel createUser(UsersDto dto) {
        // Check if a project with the given ID already exists
        if (dto.username() != null && usersRepository.findByUserName(dto.username()).isPresent()) {
            throw new ProjectAlreadyExistsException(HttpStatus.BAD_REQUEST,"A user with name '" + dto.username() + "' already exists");
        }

        // If a project with the given ID doesn't exist, proceed with creating the project
        UsersModel user = convertDtoToEntity(dto, dto.username(), dto.password(), dto.email());
        return usersRepository.saveAndFlush(user);
    }

    public void editUser(Long userId, UsersDto updatedDto) {
        Optional<UsersModel> optionalUser = usersRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            throw new ProjectNotFoundException(HttpStatus.NOT_FOUND,"User not found with ID: " + userId);
        }

        // Update the fields of the existing project with the values from the updatedDto
        UsersModel existingUser = optionalUser.get();
        existingUser.setUsername(updatedDto.username());
        existingUser.setPassword(updatedDto.password());
        existingUser.setEmail(updatedDto.email());
        // You may need to update other fields as well depending on your project structure

        // Save the updated project
        usersRepository.save(existingUser);
    }

    public void deleteUserById(Long userId) {
        usersRepository.deleteById(userId);
    }

}
