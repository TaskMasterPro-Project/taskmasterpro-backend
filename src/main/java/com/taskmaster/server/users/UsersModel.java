package com.taskmaster.server.users;

import javax.persistence.*;
import com.taskmaster.server.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UsersModel extends BaseEntity
{
    @Unique
    @Column( name = "username")
    private String username;

    @Column(name="password")
    private String password;

    @Unique
    @Column(name="email")
    private String email;

    public UsersModel() {
    }
}
