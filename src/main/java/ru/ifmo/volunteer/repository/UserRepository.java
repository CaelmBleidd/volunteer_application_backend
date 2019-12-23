package ru.ifmo.volunteer.repository;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ifmo.volunteer.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Query(
      value = "SELECT * FROM users WHERE login=:login AND password=crypt(:password, password)",
      nativeQuery = true)
  Optional<User> findByLoginAndPassword(
          @Param("login") String login, @Param("password") String password);

  @Query(
          value = "SELECT * FROM users WHERE id = :userId",
          nativeQuery = true
  )
  Optional<User> findById(@Param("userId") Long userId);

  @Query(
      value =
          "SELECT * FROM users WHERE id IN "
              + "(SELECT user_id FROM user_to_event WHERE event_id = :eventId)",
      nativeQuery = true)
  List<User> getParticipantsById(@Param("eventId") Long eventId);

  @Transactional
  @Modifying
  @Query(
      value = "UPDATE users SET password=crypt(:password, gen_salt('bf',8)) WHERE login = :login",
      nativeQuery = true)
  void register(@Param("login") String login, @Param("password") String password);

  @Query(value = "SELECT rating from users WHERE id = :id", nativeQuery = true)
  Long getRating(@Param("id") Long id);

  @Transactional
  @Modifying
  @Query(value = "UPDATE users SET rating = :rating WHERE id = :id", nativeQuery = true)
  void updateRating(Long id, Long rating);
}
