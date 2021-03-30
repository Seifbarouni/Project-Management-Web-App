package app.tasksmicroservice.Data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.tasksmicroservice.Models.TaskModel;

public interface TasksRepository extends JpaRepository<TaskModel, Long> {
    Optional<List<TaskModel>> findByProjectId(Long id);
}
