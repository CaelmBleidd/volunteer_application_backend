package ru.ifmo.volunteer.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import ru.ifmo.volunteer.dto.TaskTo;
import ru.ifmo.volunteer.model.Task;
import ru.ifmo.volunteer.service.TaskService;

@RestController
@RequestMapping("api/v1/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation(
            value = "Получение списка всех заданий для конкретного пользователя на событие",
            produces = "application/json",
            response = TaskTo.class,
            responseContainer = "List"
    )
    @GetMapping("{userId}/tasks")
    public List<TaskTo> getAllTasksForUserByEventId(@PathVariable long userId) {
        return taskService.getAllTasksForUserByEventId(userId);
    }

    @ApiOperation(
            value = "Создание новой таски",
            produces = "application/json",
            response = TaskTo.class
    )
    @PostMapping("/{userId}/createTask")
    public TaskTo create(@RequestBody TaskTo task, @PathVariable long userId) {
        return taskService.create(task, userId);
    }

    @ApiOperation(
            value = "Изменить таску и вернуть её",
            produces = "application/json",
            response = TaskTo.class
    )
    @PutMapping
    public TaskTo update(@RequestBody Task task) {
        return taskService.update(task);
    }

    @ApiOperation(
            value = "Изменить статус таски и вернуть её",
            produces = "application/json"
    )
    @PutMapping("/{taskId}/")
    public void updateTaskStatus(@PathVariable long taskId, @RequestParam("status") String status) {
        taskService.updateStatus(taskId, status);
    }

    @ApiOperation(
            value = "Получения списка всех заданий для события",
            produces = "application/json",
            response = Task.class,
            responseContainer = "List")
    @GetMapping("/tasks")
    public List<Task> getAllTasksByEventId(@RequestParam("eventId") final long id) {
        return taskService.getAllTasksByEventId(id);
    }
}