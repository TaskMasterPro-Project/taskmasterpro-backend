package com.taskmaster.server.roles;

import javax.persistence.*;

import com.taskmaster.server.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;


@Entity
@Table(name = "roles")
@Getter
@Setter
public class RolesModel extends BaseEntity{
    @Unique
    @Column(name = "name")
    private String name;

    RolesModel() {
    }

}
