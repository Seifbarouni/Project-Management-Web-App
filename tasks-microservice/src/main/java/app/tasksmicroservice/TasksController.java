package app.tasksmicroservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.tasksmicroservice.Services.TasksService;
import app.tasksmicroservice.Models.TaskModel;

@RestController()
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TasksService tasksService;

    @GetMapping("/{id}")
    @Cacheable(value = "tasksByPrId", key = "#root.args[0]")
    public List<TaskModel> getTasks(@PathVariable(name = "id") Long id) {
        if (id != 0) {
            return tasksService.getTasksByProjectId(id);
        }
        return new ArrayList<TaskModel>();
    }

    @PutMapping("/{taskId}")
    @CacheEvict(value = "tasksByPrId", key = "#root.args[1].projectId")
    @CrossOrigin(origins = "http://localhost:8080")
    public Callable<String> updateTask(@PathVariable(name = "taskId") Long taskId, @RequestBody TaskModel task)
            throws InterruptedException, ExecutionException {
        return () -> {
            return tasksService.updateTask(task).get();
        };
    }

    @PostMapping("/")
    @CacheEvict(value = "tasksByPrId", key = "#root.args[0].projectId")
    @CrossOrigin(origins = "http://localhost:8080")
    public Callable<String> addTask(@RequestBody TaskModel task) throws InterruptedException, ExecutionException {
        return () -> {
            return tasksService.addTask(task).get();
        };
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "tasksByPrId", allEntries = true)
    @CrossOrigin(origins = "http://localhost:8080")
    public Callable<String> deleteTask(@PathVariable(name = "id") Long taskId) {
        return () -> {
            return tasksService.deleteTask(taskId);
        };
    }

}
