package com.taskmaster.server.project_users;

import com.taskmaster.server.projects.ProjectsModel;
import com.taskmaster.server.roles.RolesModel;
import com.taskmaster.server.users.UsersModel;

import javax.persistence.*;


@Entity
@Table(name = "project_users")
class ProjectUsers {

    @EmbeddedId
    ProjectUsersKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    UsersModel user;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    ProjectsModel project;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    RolesModel role;

}
