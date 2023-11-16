package com.taskmaster.server.projects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectsRepository extends JpaRepository<ProjectsModel, Long> {
    @Override
    Optional<ProjectsModel> findById(Long id); // Add this method for finding a project by ID
}
