package ru.ifmo.volunteer.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import ru.ifmo.volunteer.dto.TaskTo;
import ru.ifmo.volunteer.exception.ResourceNotFoundException;
import ru.ifmo.volunteer.model.Event;
import ru.ifmo.volunteer.model.Task;
import ru.ifmo.volunteer.repository.EventRepository;
import ru.ifmo.volunteer.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final EventRepository eventRepository;

    public TaskService(final TaskRepository taskRepository, EventRepository eventRepository) {
        this.taskRepository = taskRepository;
        this.eventRepository = eventRepository;
    }

    public List<Task> getAllTasksByEventId(final long id) {
        return taskRepository.findAllById(taskRepository.findAllTasksByEventId(id));
    }

    private Optional<Long> nearestEventId(long userId) {
        return eventRepository.findNearestEventId(userId).stream()
                       .filter(event -> event.getEndDate() > System.currentTimeMillis())
                       .min(Comparator.comparing(Event::getStartDate))
                       .map(Event::getId);
    }

    public List<TaskTo> getAllTasksForUserByEventId(long userId) {
        Optional<Long> eventId = nearestEventId(userId);
        return eventId.map(aLong -> taskRepository.findAllById(taskRepository.findAllTasksByEventId(aLong)).stream()
                                                  .map(task -> new TaskTo(task.getId(),
                                                                          task.getTitle(),
                                                                          task.getDescription(),
                                                                          taskRepository.getStatus(task.getId())))
                                                  .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    public TaskTo create(TaskTo task, long userId) {
        Task tmpTask = new Task(task.getId(), task.getTitle(), task.getDescription());
        Task savedTask = taskRepository.save(tmpTask);
        taskRepository.addStatus(task.getId(), "new");
        Optional<Long> eventId = nearestEventId(userId);
        if (!eventId.isPresent()) {
            throw new ResourceNotFoundException("Вы не зарегистрированы ни на одно событие");
        }
        taskRepository.saveTaskToEvent(task.getId(), eventId.get());
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