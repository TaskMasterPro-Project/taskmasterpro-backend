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

    @PostMapping("/labels/{projectId}/{taskId}")
    public ResponseEntity<ResponseDTO> createLabel(
            @RequestBody
            CreateEditLabelRequest dto,
            @PathVariable Long projectId,
            @PathVariable Long taskId) {
        labelsService.createLabel(dto, projectId, taskId);
        return new ResponseEntity<>(new ResponseDTO("Label created successfully"), HttpStatus.CREATED);
    }
}
