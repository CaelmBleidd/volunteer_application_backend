package ru.ifmo.volunteer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import ru.ifmo.volunteer.dto.TaskTo;
import ru.ifmo.volunteer.exception.ResourceNotFoundException;
import ru.ifmo.volunteer.model.Task;
import ru.ifmo.volunteer.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasksByEventId(final long id) {
        return taskRepository.findAllById(taskRepository.findAllTasksByEventId(id));
    }

    public List<TaskTo> getAllTasksForUserByEventId(long eventId, long userId) {
        return taskRepository.findAllById(taskRepository.findAllTasksByEventId(eventId)).stream()
                             .map(task -> new TaskTo(task.getId(),
                                                     task.getTitle(),
                                                     task.getDescription(),
                                                     taskRepository.getStatus(task.getId())))
                             .collect(Collectors.toList());
    }

    public TaskTo create(Task task, long eventId) {
        Task savedTask = taskRepository.save(task);
        taskRepository.addStatus(task.getId(), "new");
        taskRepository.saveTaskToEvent(task.getId(), eventId);
        return new TaskTo(savedTask.getId(), savedTask.getTitle(), savedTask.getDescription(), "new");
    }

    public TaskTo update(Task task) {
        taskRepository
                .findById(task.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Нет задачи с таким номером"));
        Task newTask = taskRepository.save(task);
        return new TaskTo(newTask.getId(),
                          newTask.getTitle(),
                          newTask.getDescription(),
                          taskRepository.getStatus(newTask.getId()));
    }

    public void updateStatus(long taskId, String status) {
        taskRepository.updateStatus(taskId, status);
    }
}