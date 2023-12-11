package com.taskmaster.server.domain.comment;

import com.taskmaster.server.auth.model.UserModel;
import com.taskmaster.server.domain.comment.model.CommentModel;
import com.taskmaster.server.domain.membership.model.ProjectUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface CommentsRepository extends JpaRepository<CommentModel, Long>{
    List<CommentModel> findAllByTaskId(Long taskId);

    boolean existsByCommentIdAndUserId(final Long commentId, final Long userId);
}
