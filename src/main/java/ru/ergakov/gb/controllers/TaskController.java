package ru.ergakov.gb.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ergakov.gb.models.Task;
import ru.ergakov.gb.models.TaskStatus;
import ru.ergakov.gb.services.TaskService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @GetMapping()
    public List<Task> getAllBook(){
        return taskService.getAllTasks();
    }

    @PostMapping()//localhost:8080/tasks/param?description= ...
    @ResponseBody
    public Task userAddFromParam(@RequestParam("description") String description) {
        Task task = taskService.createTask(description);
        return taskService.saveTask(task);
    }

    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable TaskStatus status){
        return taskService.findTaskByStatus(status);
    }

    @PutMapping("/{id}")
    public Task updateTaskStatus(@PathVariable Long id){
        return taskService.updateTaskStatus(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }

}
