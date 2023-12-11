package com.taskmaster.server.domain.comment.dto;

import com.taskmaster.server.auth.model.UserModel;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private int id;
    private String content;
    private UserModel commentOwner;
}
