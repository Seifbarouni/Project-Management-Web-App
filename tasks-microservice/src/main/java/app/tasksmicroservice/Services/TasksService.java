package app.tasksmicroservice.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import app.tasksmicroservice.Data.TasksRepository;
import app.tasksmicroservice.Models.TaskModel;

@Service
public class TasksService {

    @Autowired
    private TasksRepository tasksRepo;

    public List<TaskModel> getTasksByProjectId(Long id) {
        Optional<List<TaskModel>> res = tasksRepo.findByProjectId(id);
        if (res.isPresent()) {
            return res.get();
        }
        return new ArrayList<TaskModel>();
    }

    @Async
    public CompletableFuture<String> updateTask(TaskModel task) {
        if (tasksRepo.existsById(task.getId())) {
            tasksRepo.saveAndFlush(task);
            return CompletableFuture.completedFuture("Success");

        }
        return CompletableFuture.completedFuture("Cannot find task");
    }

    @Async
    public CompletableFuture<String> addTask(TaskModel task) {
        TaskModel tsk = tasksRepo.save(task);
        if (tsk != null) {
            return CompletableFuture.completedFuture("Success");
        }
        return CompletableFuture.completedFuture("Cannot add task");
    }

    @Async
    public String deleteTask(Long id) {
        if (tasksRepo.existsById(id)) {
            tasksRepo.deleteById(id);
            return "Success";
        }
        return "Cannot find task";
    }

}
