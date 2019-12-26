package ru.ifmo.volunteer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import ru.ifmo.volunteer.dto.EventTo;
import ru.ifmo.volunteer.exception.AlreadyExistsException;
import ru.ifmo.volunteer.exception.ResourceNotFoundException;
import ru.ifmo.volunteer.model.Event;
import ru.ifmo.volunteer.model.User;
import ru.ifmo.volunteer.repository.EventRepository;
import ru.ifmo.volunteer.repository.UserRepository;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventService(final EventRepository eventRepository, final UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public List<Event> read() {
        return eventRepository.findAll();
    }

    public void deleteById(final long id) {
        eventRepository.deleteById(id);
    }

    public Event findById(final long id) {
        return eventRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Событие с id %d не найдено", id)));
    }

    public Event add(final Event event) {
        eventRepository
                .findById(event.getId())
                .ifPresent(
                        e -> {
                            throw new AlreadyExistsException(
                                    String.format("Event with %d id already exists", event.getId()));
                        });
        return eventRepository.save(event);
    }

    public void subscribe(final long userId, final long eventId) {
        eventRepository
                .findEventWithUserId(userId, eventId)
                .ifPresent(
                        u -> {
                            throw new AlreadyExistsException("Вы уже были подписаны на это событие");
                        });
        eventRepository.subscribe(userId, eventId);
    }

    public void unsubscribe(final long userId, final long eventId) {
        eventRepository
                .findEventWithUserId(userId, eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Вы не были подписаны на это событие"));
        eventRepository.unsubscribe(userId, eventId);
    }


    public Event update(final Event event) {
        findById(event.getId());
        return eventRepository.save(event);
    }

    public List<EventTo> getActualForUser(final long id) {
        return eventRepository.findAll().stream()
                              .filter(event -> event.getEndDate()> System.currentTimeMillis())
                              .map(event -> new EventTo(event.getId(),
                                                        event.getStartDate(),
                                                        event.getEndDate(),
                                                        event.getTitle(),
                                                        event.getDescription(),
                                                        event.getLocation(),
                                                        isStarred(id, event.getId())))
                              .collect(Collectors.toList());
    }

    private boolean isStarred(long userId, long eventId) {
        return eventRepository.isStarred(userId, eventId) > 0;
    }

}
