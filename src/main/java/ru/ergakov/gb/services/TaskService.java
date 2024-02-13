package ru.ergakov.gb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ergakov.gb.models.Task;
import ru.ergakov.gb.models.TaskStatus;
import ru.ergakov.gb.repositoryes.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return repository.findById(id);
    }

    public Task saveTask(Task task) {
        return repository.save(task);
    }

    public Task createTask(String description){
        Task newTask = new Task();
        newTask.setDescription(description);
        newTask.setStatus(TaskStatus.NOT_STARTED);
        newTask.setDateCreate(LocalDateTime.now());
        return newTask;
    }

    public Task updateTaskStatus(Long id){
        Optional<Task> optionalTask = repository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            if (task.getStatus().equals(TaskStatus.NOT_STARTED)){
                task.setStatus(TaskStatus.IN_PROGRESS);
            } else if (task.getStatus().equals(TaskStatus.IN_PROGRESS)){
                task.setStatus(TaskStatus.COMPLETED);
            }
            return repository.save(task);
        } else {
            throw new IllegalArgumentException("Task not found with id: " + id);
        }
    }


    public List<Task> findTaskByStatus(TaskStatus status){
        return repository.findTasksByStatus(status);
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }
}
