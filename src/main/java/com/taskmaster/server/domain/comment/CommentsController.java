package com.taskmaster.server.domain.comment;


import com.taskmaster.server.domain.comment.dto.CommentDTO;
import com.taskmaster.server.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.taskmaster.server.config.AppConstants.API_BASE;

@RestController
@RequestMapping(path = API_BASE + "/v1")
public class CommentsController {
    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService)
    {
        this.commentsService = commentsService;
    }

    @GetMapping("/tasks/{taskId}/comments")
    public List<CommentDTO> getAllCommentsForTask(@PathVariable(value = "taskId") Long taskId)
    {
        return commentsService.getAllCommentsForTask(taskId);
    }

    @PostMapping("/tasks/{taskId}/comments")
    @PreAuthorize("@securityUtility.isTaskOwner(#taskId, principal)")
    public ResponseEntity<ResponseDTO> addCommentToTask(
            @PathVariable(value = "taskId") Long taskId,
            @RequestBody CommentsController.CreateUpdateCommentRequest request)
    {
        commentsService.addCommentToTask(taskId, request.content());
        return new ResponseEntity<>(new ResponseDTO("Comment created successfully!!"), null, 201);
    }

    @DeleteMapping("/tasks/{taskId}/comments/{commentId}")
    @PreAuthorize("@securityUtility.isTaskOwner(#taskId, principal)")
    public ResponseEntity<ResponseDTO> deleteTaskComment(@PathVariable(value = "taskId") Long taskId,
                                                             @PathVariable(value = "commentId") Long commentId)
    {
        commentsService.deleteTaskComment(taskId, commentId);
        return new ResponseEntity<>(new ResponseDTO("Comment deleted successfully!!"), null, 200);
    }

    @PutMapping("/tasks/{taskId}/comments/{commentId}")
    @PreAuthorize("@securityUtility.isTaskOwner(#taskId, principal)")
    public ResponseEntity<ResponseDTO> updateTaskComment(@PathVariable(value = "taskId") Long taskId,
                                                             @PathVariable(value = "commentId") Long commentId,
                                                             @RequestBody CommentsController.CreateUpdateCommentRequest request)
    {
        commentsService.updateTaskComment(commentId, request.content());
        return new ResponseEntity<>(new ResponseDTO("Comment updated successfully!!"), null, 200);
    }

    public record CreateUpdateCommentRequest(String content)
    {
    }
}
