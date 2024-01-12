package com.taskmaster.server.domain.label.model;

import com.taskmaster.server.domain.project.model.ProjectModel;
import com.taskmaster.server.domain.task.model.TaskModel;
import com.taskmaster.server.model.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "labels")
public class LabelModel extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="project_id")
    private ProjectModel project;
}
