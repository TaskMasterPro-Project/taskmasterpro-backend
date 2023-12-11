package com.taskmaster.server.domain.task;

import com.taskmaster.server.auth.UserRepository;
import com.taskmaster.server.auth.model.UserModel;
import com.taskmaster.server.auth.security.UserPrincipal;
import com.taskmaster.server.domain.task.dto.CreateEditTaskRequest;
import com.taskmaster.server.domain.task.dto.TaskDTO;
import com.taskmaster.server.domain.task.model.TaskModel;
import com.taskmaster.server.dto.UserDTO;
import com.taskmaster.server.exception.NotAuthorizedException;
import com.taskmaster.server.exception.TaskAlreadyExistsException;
import com.taskmaster.server.exception.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TasksService {

    private final TasksRepository tasksRepository;
    private final UserRepository userRepository;

    public TasksService(TasksRepository tasksRepository,
                           UserRepository userRepository)
    {
        this.tasksRepository = tasksRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createTask(CreateEditTaskRequest dto, UserPrincipal userPrincipal)
    {
        UserModel loggedUser = userRepository
                .findByUsernameOrEmail(userPrincipal.getUsername(), userPrincipal.getUsername())
                .orElseThrow(NotAuthorizedException::new);
        List<UserModel> assignees = userRepository.findAllByUsernameIn(dto.assignees());
        if (dto.title() != null && tasksRepository.existsByTitle(dto.title()))
        {
            throw new TaskAlreadyExistsException(HttpStatus.BAD_REQUEST,
                    "Task with title '" + dto.title() + "' already exists");
        }

        var model = TaskModel.builder()
                .title(dto.title())
                .description(dto.description())
                             .assignees(assignees)
                .dueDate(dto.dueDate())
                             .taskOwner(loggedUser)
                .build();
        tasksRepository.save(model);
    }

    @Transactional
    public void editTask(long taskId, CreateEditTaskRequest updatedDto)
    {
        List<UserModel> assignees = userRepository.findAllByUsernameIn(updatedDto.assignees());
        TaskModel task = tasksRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        task.setTitle(updatedDto.title());
        task.setDescription(updatedDto.description());
        task.setAssignees(assignees);
        task.setDueDate(updatedDto.dueDate());
        tasksRepository.save(task);
    }

    @Transactional
    public void deleteTaskById(long taskId)
    {
        tasksRepository.deleteById(taskId);
    }

    public List<TaskDTO> getTasksForProject(Long projectId) {
        List<TaskModel> taskModels = tasksRepository.findAllByProjectId(projectId);
        return taskModels.stream().map(taskModel -> {
            List<UserDTO> assigneeDTOs = taskModel.getAssignees().stream()
                                                  .map(userModel -> new UserDTO(userModel.getFirstName(), userModel.getLastName(), userModel.getUsername(), userModel.getEmail()))
                                                  .toList();

            UserDTO taskOwnerDTO = null;
            if (taskModel.getTaskOwner() != null) {
                UserModel taskOwner = taskModel.getTaskOwner();
                taskOwnerDTO = new UserDTO(taskOwner.getFirstName(), taskOwner.getLastName(), taskOwner.getUsername(), taskOwner.getEmail());
            }

            return new TaskDTO(taskModel.getId(), taskModel.getTitle(), taskModel.getDescription(), assigneeDTOs, taskModel.getDueDate(), taskOwnerDTO);
        }).toList();
    }



}
