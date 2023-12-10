package com.taskmaster.server.domain.task.model;

import javax.persistence.*;

import com.taskmaster.server.auth.model.UserModel;
import com.taskmaster.server.domain.comment.model.CommentModel;
import com.taskmaster.server.domain.project.model.ProjectModel;
import com.taskmaster.server.model.BaseEntity;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class TaskModel extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectModel project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentModel> comments;

    @Column(columnDefinition = "TEXT")
    private String description;

}
