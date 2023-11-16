package com.taskmaster.server.projects;

import javax.persistence.*;

import com.taskmaster.server.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "projects")
@Getter
@Setter
public class ProjectsModel extends BaseEntity {

    @Column( name = "name")
    private String name;

    public ProjectsModel() {
    }


}
