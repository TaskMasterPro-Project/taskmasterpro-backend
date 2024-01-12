package com.taskmaster.server.domain.label;

import com.taskmaster.server.auth.security.UserPrincipal;
import com.taskmaster.server.domain.label.dto.CreateEditLabelRequest;
import com.taskmaster.server.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.taskmaster.server.config.AppConstants.API_BASE;

@RequestMapping(path = API_BASE + "/v1")
@RestController
public class LabelsController
{
    private final LabelsService labelsService;

    public LabelsController(LabelsService labelsService)
    {
        this.labelsService = labelsService;
    }

    @PostMapping("/projects/{projectId}/labels")
    public ResponseEntity<ResponseDTO> createLabel(
            @PathVariable long projectId,
            @RequestBody CreateEditLabelRequest dto) {
        labelsService.createLabel(dto, projectId);
        return new ResponseEntity<>(new ResponseDTO("Label created successfully"), HttpStatus.CREATED);
    }

    @DeleteMapping("/projects/{projectId}/labels/{labelId}")
    public ResponseEntity<ResponseDTO> deleteLabel(
            @PathVariable long projectId,
            @PathVariable long labelId) {
        labelsService.deleteLabelById(labelId);
        return new ResponseEntity<>(new ResponseDTO("Label deleted successfully"), HttpStatus.OK);
    }

    @GetMapping("/projects/{projectId}/labels")
    public ResponseEntity<ResponseDTO> getLabelsForProject(
            @PathVariable long projectId) {
        var labels = labelsService.getLabelsForProject(projectId);
        return new ResponseEntity<>(new ResponseDTO("Labels fetched successfully", labels), HttpStatus.OK);
    }
}
