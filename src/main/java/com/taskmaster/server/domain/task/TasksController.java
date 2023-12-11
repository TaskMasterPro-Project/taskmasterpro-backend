package com.taskmaster.server.domain.task;

import com.taskmaster.server.auth.security.UserPrincipal;
import com.taskmaster.server.domain.task.dto.CreateEditTaskRequest;
import com.taskmaster.server.domain.task.dto.TaskDTO;
import com.taskmaster.server.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.taskmaster.server.config.AppConstants.API_BASE;

@RestController
@RequestMapping(path = API_BASE + "/v1/tasks")
public class TasksController {
    private final TasksService tasksService;

    public TasksController(TasksService tasksService)
    {

        this.tasksService = tasksService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createTask(
            @RequestBody
            CreateEditTaskRequest dto,
            @AuthenticationPrincipal UserPrincipal principal)
    {
        tasksService.createTask(dto, principal);
        return new ResponseEntity<>(new ResponseDTO("Task created successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ResponseDTO> getTaskById(@PathVariable Long taskId)
    {
        return new ResponseEntity<>(new ResponseDTO(null, tasksService.getTask(taskId)), HttpStatus.OK);
    }


    @PutMapping("/{taskId}")
    @PreAuthorize("@securityUtility.isTaskOwner(#taskId, principal) and @securityUtility.isProjectOwner(#projectId, principal)")
    public ResponseEntity<ResponseDTO> editTask(
            @PathVariable Long taskId,
            @RequestBody
            CreateEditTaskRequest updatedDto)
    {
        tasksService.editTask(taskId, updatedDto);
        return new ResponseEntity<>(new ResponseDTO("Task updated successfully!"), HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("@securityUtility.isTaskOwner(#taskId, principal) and @securityUtility.isProjectOwner(#projectId, principal)")
    public ResponseEntity<ResponseDTO> deleteTask(@PathVariable Long taskId)
    {
        tasksService.deleteTaskById(taskId);
        return new ResponseEntity<>(new ResponseDTO("Task deleted successfully!"), HttpStatus.OK);
    }
}
