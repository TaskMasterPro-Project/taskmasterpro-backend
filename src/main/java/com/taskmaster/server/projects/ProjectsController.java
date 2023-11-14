package com.taskmaster.server.projects;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectsController {
    private final IProjectsService projectsService;

    @GetMapping("/get-all")
    public List<ProjectsModel> getProjects() {
        return projectsService.getAllProjects();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody ProjectsDto dto) {
        try {
            ProjectsModel projectCreate = projectsService.createProject(dto);
            return new ResponseEntity<>(projectCreate, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable Long projectId) {
        Optional<ProjectsModel> project = projectsService.findProjectById(projectId);
        return project.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
              //.orElseGet(() -> new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND));
              // check how to fix this
    }



    @PutMapping("/edit/{projectId}")
    public ResponseEntity<?> editProject(
            @PathVariable Long projectId,
            @RequestBody ProjectsDto updatedDto
    ) {
        try {
            projectsService.editProject(projectId, updatedDto);
            return new ResponseEntity<>("Project updated successfully", HttpStatus.OK);
        } catch (ProjectNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        try {
            projectsService.deleteProjectById(projectId);
            return new ResponseEntity<>("Project deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
