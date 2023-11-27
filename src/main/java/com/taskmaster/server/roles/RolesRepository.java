package com.taskmaster.server.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RolesRepository extends JpaRepository<RolesModel, Long>{
    Optional<RolesModel> findByName (String name);
}
