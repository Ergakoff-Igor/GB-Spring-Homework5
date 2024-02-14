package ru.ergakov.gb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ergakov.gb.models.Task;
import ru.ergakov.gb.models.TaskStatus;
import ru.ergakov.gb.repositoryes.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для передачи запросов в репозиторий
 */
@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    /**
     * Метод получения списка всех задач
     *
     * @return список задач
     */
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    /**
     * Метод сохранения задачи
     *
     * @param task задача
     * @return сохраненная задача
     */
    private Task saveTask(Task task) {
        return repository.save(task);
    }

    /**
     * Метод добавления задачи
     *
     * @param description описание задачи
     * @return новая задача
     */
    public Task createTask(String description) {
        Task newTask = new Task();
        newTask.setDescription(description);
        newTask.setStatus(TaskStatus.NOT_STARTED);
        newTask.setDateCreate(LocalDateTime.now());
        return saveTask(newTask);
    }

    /**
     * Метод автоматического изменения статуса задачи при каждом запросе
     *
     * @param id id задачи
     * @return обновленная задача
     */
    public Task updateTaskStatus(Long id) {
        Optional<Task> optionalTask = repository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            if (task.getStatus().equals(TaskStatus.NOT_STARTED)) {
                task.setStatus(TaskStatus.IN_PROGRESS);
            } else if (task.getStatus().equals(TaskStatus.IN_PROGRESS)) {
                task.setStatus(TaskStatus.COMPLETED);
            }
            return repository.save(task);
        } else {
            throw new IllegalArgumentException("Task not found with id: " + id);
        }
    }


    /**
     * Метод поиска списка задач по статусу
     *
     * @param status искомый статус
     * @return список задач
     */
    public List<Task> findTaskByStatus(TaskStatus status) {
        return repository.findTasksByStatus(status);
    }

    /**
     * Метод удаления задачи по id
     *
     * @param id id задачи
     */
    public void deleteTask(Long id) {
        repository.deleteById(id);
    }
}
