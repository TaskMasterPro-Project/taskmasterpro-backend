package com.taskmaster.server.domain.task;

import com.taskmaster.server.domain.task.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TasksRepository extends JpaRepository<TaskModel, Long>{
    //Optional<TaskModel> findByName(String name);
    //Not sure whether we need it... we search with the task's id anyway

    boolean existsByName(String name);

}
