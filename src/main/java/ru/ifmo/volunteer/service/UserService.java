package ru.ifmo.volunteer.service;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ru.ifmo.volunteer.exception.AlreadyExistsException;
import ru.ifmo.volunteer.exception.AuthorizationException;
import ru.ifmo.volunteer.exception.ResourceNotFoundException;
import ru.ifmo.volunteer.model.Event;
import ru.ifmo.volunteer.model.User;
import ru.ifmo.volunteer.repository.EventRepository;
import ru.ifmo.volunteer.repository.UserRepository;

@Service
public class UserService {

  private  UserRepository userRepository;

    public UserService( UserRepository userRepository) {
    this.userRepository = userRepository;
    }

  public User findById(long id) {
    return userRepository
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    String.format("Представитель с id %d не найден", id)));
  }

  public User add( User user) {
    if (userRepository.findById(user.getId()).isPresent()) {
      throw new AlreadyExistsException(
          String.format("User with %d id already exists", user.getId()));
    }
    if (user.getRating() == null) user.setRating(0L);
    return userRepository.save(user);
  }

  public User update( User user) {
    findById(user.getId());
    return userRepository.save(user);
  }

  public User authorize( String login,  String password) {
    return userRepository
        .findByLoginAndPassword(login, password)
        .orElseThrow(() -> new AuthorizationException("Неверный логин или пароль"));
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public void deleteById( long id) {
    userRepository.deleteById(id);
  }

  public List<User> getParticipantsById( long id) {
    return userRepository.getParticipantsById(id);
  }

  public void register( String login,  String password) {
    userRepository.register(login, password);
  }

  public Long getRating( long id) {
    return userRepository.getRating(id);
  }

  public void updateRating( long rating,  long id) {
    userRepository.updateRating(rating, id);
  }
}
