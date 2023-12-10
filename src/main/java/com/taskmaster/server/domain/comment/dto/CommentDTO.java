package com.taskmaster.server.domain.comment.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private int id;
    private String content;
}
