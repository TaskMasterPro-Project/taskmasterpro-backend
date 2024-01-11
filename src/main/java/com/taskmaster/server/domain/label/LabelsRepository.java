package com.taskmaster.server.domain.label;

import com.taskmaster.server.domain.label.model.LabelModel;
import com.taskmaster.server.domain.task.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelsRepository extends JpaRepository<LabelModel, Long>
{
    boolean existsByNameAndProjectId(String name, long projectId);
    List<LabelModel> findAllByProjectId(Long projectId);
    List<LabelModel> findAllByTaskId(Long taskId);
}
