package com.taskmaster.server.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersModel, Long>{
    Optional<UsersModel> findByUserName(String username);
}
