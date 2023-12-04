package com.taskmaster.server.project_users;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
class ProjectUsersKey implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "project_id")
    Long projectId;

    @Column(name = "role_id")
    Long roleId;
}

