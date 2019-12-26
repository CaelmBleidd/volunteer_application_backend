package ru.ifmo.volunteer.controller;

import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.volunteer.dto.EventTo;
import ru.ifmo.volunteer.model.Event;
import ru.ifmo.volunteer.service.EventService;

@RestController
@RequestMapping("api/v1/event")
public class EventController {
    private final EventService eventService;

    public EventController(final EventService eventService) {
        this.eventService = eventService;
    }

    @ApiOperation(
            value = "Добавляет событие и возвращает его",
            produces = "application/json",
            response = Event.class)
    @PostMapping
    public Event create(@RequestBody final Event event) {
        return eventService.add(event);
    }

    @ApiOperation(
            value = "Возвращает событие по id",
            produces = "application/json",
            response = Event.class)
    @GetMapping("{id}")
    public Event findEventById(@PathVariable final long id) {
        return eventService.findById(id);
    }

    @ApiOperation(
            value = "Возвращает список событий для юзера",
            produces = "application/json",
            response = Event.class,
            responseContainer = "List")
    @GetMapping("{id}/events")
    public List<EventTo> getActualForUser(@PathVariable final long id) {
        return eventService.getActualForUser(id);
    }

    @ApiOperation(value = "Отписаться от события", produces = "application/json")
    @DeleteMapping("/unsubscribe")
    public void unsubscribe(@RequestParam final long userId, @RequestParam final long eventId) {
        eventService.unsubscribe(userId, eventId);
    }

    @ApiOperation(value = "Подписаться на событие", produces = "application/json")
    @PostMapping("/subscribe")
    public void subscribe(@RequestParam final long userId, @RequestParam final long eventId) {
        eventService.subscribe(userId, eventId);
    }

    @ApiOperation(
            value = "Изменяет поля события и возвращает измененного события",
            produces = "application/json",
            response = Event.class)
    @PutMapping
    public Event update(@RequestBody final Event event) {
        return eventService.update(event);
    }

    @ApiOperation(
            value = "Возвращает список всех событий",
            produces = "application/json",
            response = Event.class,
            responseContainer = "List")
    @GetMapping
    public List<Event> read() {
        return eventService.read();
    }

    @ApiOperation(
            value = "Удаляет событие по id",
            produces = "application/json",
            response = Event.class)
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable final long id) {
        eventService.deleteById(id);
    }
}
