package com.taskmaster.server.projects;

import javax.persistence.*;

import com.taskmaster.server.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;


@Entity
@Table(name = "projects")
@Getter
@Setter
public class ProjectsModel extends BaseEntity {

    @Unique
    @Column( name = "name")
    private String name;

    public ProjectsModel() {
    }


}
