package com.taskmaster.server.domain.task;

import com.taskmaster.server.domain.task.dto.TaskDTO;
import com.taskmaster.server.domain.task.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TasksRepository extends JpaRepository<TaskModel, Long> {
    //Optional<TaskModel> findByName(String name);
    //Not sure whether we need it... we search with the task's id anyway

    boolean existsByName(String name);

//    @Override
//    @Query("SELECT new com.taskmaster.server.domain.task.dto.TaskDTO.assignees(t.id, t.title, t.description,"+
//            "t.assignees, t.dueDate, t.taskOwner, u.firstName, " +
//            "u.lastName, u.username) " +
//            "FROM TaskModel as t")
//    Optional<TaskDTO> findById(Long taskId);

    boolean existsByTaskIdAndUserId(final Long taskId, final Long userId);


}
