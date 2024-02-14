package ru.ergakov.gb.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ergakov.gb.models.Task;
import ru.ergakov.gb.models.TaskStatus;
import ru.ergakov.gb.services.TaskService;

import java.util.List;

/**
 * Класс - контроллер, используется для обработки запросов
 */
@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    /**
     * Метод обработки GET запроса без параметров
     * //localhost:8080/tasks
     *
     * @return список всех задач
     */
    @GetMapping()
    public List<Task> getAllTask() {
        return taskService.getAllTasks();
    }

    /**
     * POST метод обработки запроса добавления задачи через параметр
     * //localhost:8080/tasks/param?description= ...
     *
     * @param description тело задачи, передается через параметр
     * @return новая задача
     */
    @PostMapping()
    @ResponseBody
    public Task addTaskFromParam(@RequestParam("description") String description) {
        return taskService.createTask(description);
    }

    /**
     * GET метод обработки запроса списка задач с указанным статусом
     * //localhost:8080/tasks/status/(NOT_STARTED, IN_PROGRESS, COMPLETED)
     *
     * @param status искомый статус
     * @return список задач
     */
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable TaskStatus status) {
        return taskService.findTaskByStatus(status);
    }

    /**
     * PUT метод обработки запроса изменения статуса задачи по id
     * //localhost:8080/tasks/{id}
     *
     * @param id Id задачи
     * @return задача с измененным статусом
     */
    @PutMapping("/{id}")
    public Task updateTaskStatus(@PathVariable Long id) {
        return taskService.updateTaskStatus(id);
    }

    /**
     * DELETE метод обработки запроса удаления задачи по id
     * //localhost:8080/tasks/{id}
     *
     * @param id Id задачи
     */
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

}
