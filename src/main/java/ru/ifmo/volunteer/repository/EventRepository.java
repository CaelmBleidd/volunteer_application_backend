package ru.ifmo.volunteer.repository;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ifmo.volunteer.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(
            value =
                    "SELECT * "
                    + "FROM event "
                    + "INNER JOIN user_to_event ON user_id = id "
                    + "WHERE user_id = :id",
            nativeQuery = true)
    List<Event> findAllByUserId(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user_to_event VALUES(:userId, :eventId)", nativeQuery = true)
    void subscribe(@Param("userId") Long userId, @Param("eventId") Long eventId);

    @Transactional
    @Modifying
    @Query(
            value = "DELETE FROM user_to_event WHERE event_id = :eventId AND user_id = :userId",
            nativeQuery = true)
    void unsubscribe(@Param("userId") Long userId, @Param("eventId") Long eventId);

    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO user_to_role (user_id, role_id, event_id) VALUES (?1,?2,?3)",
            nativeQuery = true)
    void addRole(Long userId, Long roleId, Long eventId);

    @Query(value = "SELECT required_rating FROM event WHERE id = :id", nativeQuery = true)
    Long ratingRequired(@Param("id") Long id);

    @Query(
            value = "SELECT user_id FROM user_to_event WHERE user_id = :userId AND event_id = :eventId",
            nativeQuery = true)
    Optional<Long> findEventWithUserId(@Param("userId") Long userId, @Param("eventId") Long eventId);


    @Transactional
    @Modifying
    @Query(value = "UPDATE event SET finished = true WHERE id = :id", nativeQuery = true)
    void finish(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(
            value = "update event set volunteers_present = volunteers_present + 1 where id = ?1",
            nativeQuery = true)
    void increment(Long eventId);

    @Query(value = "SELECT COUNT(*) FROM user_to_event WHERE event_id = :eventId AND user_id = :userId",
           nativeQuery = true)
    int isStarred(@Param("userId") long userId, @Param("eventId") long eventId);

    @Query(
            value = "SELECT * FROM event INNER JOIN user_to_event ute on event.id = ute.event_id WHERE user_id=:userId",
            nativeQuery = true)
    List<Event> findNearestEventId(@Param("userId")long userId);
}
