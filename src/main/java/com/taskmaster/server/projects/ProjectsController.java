package com.taskmaster.server.projects;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectsController {
    private final ProjectsService projectsService;
    private final ProjectsRepository projectsRepository;

    @GetMapping("/projects")
    public List<ProjectsModel> getProjects() {
        return projectsService.getAllProjects();
    }

    @PostMapping("/projects")
    public ResponseEntity<?> createProject(@RequestBody ProjectsDto dto) {

            return new ResponseEntity<>(projectsService.createProject(dto), HttpStatus.CREATED);

    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable Long projectId) {
        Optional<ProjectsModel> project = projectsRepository.findById(projectId);
        return project.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
              //.orElseGet(() -> new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND));
              // check how to fix this
    }



    @PostMapping("/{projectId}/edit")
    public ResponseEntity<?> editProject(
            @PathVariable Long projectId,
            @RequestBody ProjectsDto updatedDto) {

            projectsService.editProject(projectId, updatedDto);
            return new ResponseEntity<>("Project updated successfully", HttpStatus.OK);

    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
            projectsService.deleteProjectById(projectId);
            return new ResponseEntity<>("Project deleted successfully", HttpStatus.OK);
    }
}
