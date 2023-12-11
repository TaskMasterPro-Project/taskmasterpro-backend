package com.taskmaster.server.domain.comment;

import com.taskmaster.server.auth.model.UserModel;
import com.taskmaster.server.domain.comment.dto.CommentDTO;
import com.taskmaster.server.domain.comment.model.CommentModel;
import com.taskmaster.server.domain.task.TasksRepository;
import com.taskmaster.server.exception.TaskNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final TasksRepository tasksRepository;
    private final ModelMapper modelMapper;

    public CommentsService(CommentsRepository commentsRepository,
                             TasksRepository tasksRepository,
                             ModelMapper modelMapper)
    {
        this.commentsRepository = commentsRepository;
        this.tasksRepository = tasksRepository;
        this.modelMapper = modelMapper;
    }

    public List<CommentDTO> getAllCommentsForTask(final Long taskId)
    {
        var comments = commentsRepository.findAllByTaskId(taskId);
        return comments.stream().map(comment -> modelMapper.map(comment, CommentDTO.class)).toList();
    }

    @Transactional
    public void addCommentToTask(final Long taskId, final String content, final UserModel commentOwner)
    {
        var task = tasksRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        var comment = CommentModel.builder()
                .content(content)
                .task(task)
                .commentOwner(commentOwner)
                .build();
        task.getComments().add(comment);
        commentsRepository.save(comment);
    }

    @Transactional
    public void deleteTaskComment(final long taskId, final Long commentId)
    {
        var comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        tasksRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new)
                .getComments()
                .remove(comment);
        commentsRepository.deleteById(commentId);
    }

    @Transactional
    public void updateTaskComment(final Long commentId, final String content)
    {
        var comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(content);
    }
}
