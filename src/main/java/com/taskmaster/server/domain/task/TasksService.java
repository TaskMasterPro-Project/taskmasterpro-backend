package com.taskmaster.server.domain.task;

import com.taskmaster.server.auth.UserRepository;
import com.taskmaster.server.auth.model.UserModel;
import com.taskmaster.server.auth.security.UserPrincipal;
import com.taskmaster.server.domain.comment.dto.CommentDTO;
import com.taskmaster.server.domain.task.dto.CreateEditTaskRequest;
import com.taskmaster.server.domain.task.dto.TaskDTO;
import com.taskmaster.server.domain.task.model.TaskModel;
import com.taskmaster.server.exception.NotAuthorizedException;
import com.taskmaster.server.exception.TaskAlreadyExistsException;
import com.taskmaster.server.exception.TaskNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TasksService {

    private final TasksRepository tasksRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public TasksService(TasksRepository tasksRepository,
                           ModelMapper modelMapper,
                           UserRepository userRepository)
    {
        this.tasksRepository = tasksRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }


    public TaskDTO getTask(long taskId)
    {
        var model = tasksRepository.findById(taskId).stream().findFirst()
                .orElseThrow(TaskNotFoundException::new);
        return modelMapper.map(model, TaskDTO.class);
    }


    @Transactional
    public void createTask(CreateEditTaskRequest dto, UserPrincipal userPrincipal)
    {
        UserModel loggedUser = userRepository
                .findByUsernameOrEmail(userPrincipal.getUsername(), userPrincipal.getUsername())
                .orElseThrow(NotAuthorizedException::new);
        if (dto.title() != null && tasksRepository.existsByName(dto.title()))
        {
            throw new TaskAlreadyExistsException(HttpStatus.BAD_REQUEST,
                    "Task with title '" + dto.title() + "' already exists");
        }

        var model = TaskModel.builder()
                .title(dto.title())
                .description(dto.description())
                .build();
        var savedModel = tasksRepository.save(model);
        tasksRepository.save(model);
    }

    @Transactional
    public void editTask(long taskId, CreateEditTaskRequest updatedDto)
    {
        TaskModel task = tasksRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        task.setTitle(updatedDto.title());
        task.setDescription(updatedDto.description());
    }

    @Transactional
    public void deleteTaskById(long taskId)
    {
        tasksRepository.deleteById(taskId);
    }
}
