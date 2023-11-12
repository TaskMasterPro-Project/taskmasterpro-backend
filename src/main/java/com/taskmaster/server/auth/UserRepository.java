package com.taskmaster.server.auth;

import com.taskmaster.server.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String usernameOrEmail);
    boolean existsByEmail(String usernameOrEmail);
}
