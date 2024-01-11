package com.taskmaster.server.domain.label.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabelDTO {
    private long id;
    private long projectId;
    private long taskId;
    private String name;
}
